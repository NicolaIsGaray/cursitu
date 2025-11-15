package pdevs.CursITU.service.group;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdevs.CursITU.controller.request.GroupCreateDTO;
import pdevs.CursITU.models.GroupEntity;
import pdevs.CursITU.models.InvitationsEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.GroupRepo;
import pdevs.CursITU.repositories.InvitationsRepo;
import pdevs.CursITU.repositories.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private InvitationsRepo invitationsRepo;

    @Transactional
    public GroupEntity createGroupFromDto(GroupCreateDTO groupDto) {

        GroupEntity nuevoGrupo = new GroupEntity();

        nuevoGrupo.setNombre(groupDto.getNombre());
        nuevoGrupo.setLimite(groupDto.getLimite());
        nuevoGrupo.setComision(groupDto.getComision());

        Set<UserEntity> miembros = new HashSet<>();
        Set<UserEntity> pendientes = new HashSet<>();

        if (groupDto.getMiembrosIds() != null && !groupDto.getMiembrosIds().isEmpty()) {

            // DEBUG 1: Comprobar qué IDs se están buscando
            System.out.println("IDs de Miembros a buscar: " + groupDto.getMiembrosIds());

            List<UserEntity> miembrosEncontrados = (List<UserEntity>) userRepo.findAllById(groupDto.getMiembrosIds());

            // DEBUG 2: Comprobar si se encontraron usuarios
            System.out.println("Número de Miembros encontrados: " + miembrosEncontrados.size());

            miembros.addAll(miembrosEncontrados);

            for (UserEntity miembro : miembrosEncontrados) {
                miembro.setHasGroup(false);
                userRepo.save(miembro);
            }

        }

        if (groupDto.getPendientesIds() != null && !groupDto.getPendientesIds().isEmpty()) {
            List<UserEntity> pendientesEncontrados = (List<UserEntity>) userRepo.findAllById(groupDto.getPendientesIds());
            pendientes.addAll(pendientesEncontrados);

            for (UserEntity pendiente : pendientesEncontrados) {
                pendiente.setHasGroup(false);
                userRepo.save(pendiente);
            }
        }

        nuevoGrupo.setMiembros(miembros);
        nuevoGrupo.setPendientes(pendientes);

        return groupRepo.save(nuevoGrupo);
    }

    @Transactional
    private void enviarInvitaciones(GroupEntity grupo) {
        for (UserEntity invitado : grupo.getPendientes()) {
            InvitationsEntity inv = new InvitationsEntity();
            inv.setGroup(grupo);
            inv.setInvitedUser(invitado);

            invitationsRepo.save(inv);

            invitado.getInvitaciones().add(inv);
            userRepo.save(invitado);
        }
    }

    public void responderInvitacion(String dniAlumno, Long groupId, boolean aceptar) {
        UserEntity alumno = userRepo.findByDni(dniAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado."));

        // 1. Encontrar la entidad de invitación específica
        InvitationsEntity invitation = invitationsRepo
                .findByInvitedUserAndGroupId(alumno, groupId)
                .orElse(null);

        // 2. Eliminar la invitación de la tabla de invitaciones
        if (invitation != null) {
            invitationsRepo.delete(invitation);
        }

        if (aceptar) {
            GroupEntity grupo = groupRepo.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado."));

            // 3. Mover al alumno al grupo de miembros
            // La eliminación de la lista de "pendientes" debe ser gestionada
            // por el Cascade/JoinTable si el GroupEntity la posee, pero lo haremos manualmente.

            grupo.getPendientes().remove(alumno);
            grupo.getMiembros().add(alumno);
            alumno.setHasGroup(true);

            groupRepo.save(grupo);
        }

        // 4. Guardar los cambios en el usuario (si 'aceptar' fue false, solo se elimina la invitación y se guarda el usuario).
        userRepo.save(alumno);
    }
}

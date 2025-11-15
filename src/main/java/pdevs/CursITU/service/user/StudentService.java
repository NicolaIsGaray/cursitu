package pdevs.CursITU.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdevs.CursITU.models.GroupEntity;
import pdevs.CursITU.models.InvitationsEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.GroupRepo;
import pdevs.CursITU.repositories.UserRepo;

import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    public UserEntity addInvitacion(Long id, InvitationsEntity invitation) {
        UserEntity alumno = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        alumno.getInvitaciones().add(invitation);
        return userRepo.save(alumno);
    }

    public UserEntity acceptInvitacion(Long alumnoId, Long groupId) {
        UserEntity alumno = userRepo.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        GroupEntity grupo = this.groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        alumno.getInvitaciones().removeIf(inv -> inv.getId() == groupId);

        alumno.setHasGroup(true);

        grupo.getPendientes().removeIf(a -> a.getId() == alumnoId);
        grupo.getMiembros().add(alumno);

        userRepo.save(alumno);
        groupRepo.save(grupo);

        return alumno;
    }


    public UserEntity rejectInvitacion(Long id, Long groupId) {
        UserEntity alumno = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        alumno.getInvitaciones().removeIf(inv -> inv.getId() == groupId);

        GroupEntity grupo = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        if (grupo.getPendientes() != null) {
            grupo.getPendientes().removeIf(a -> a.getId() == alumno.getId());
        }

        groupRepo.save(grupo);
        return userRepo.save(alumno);
    }
}

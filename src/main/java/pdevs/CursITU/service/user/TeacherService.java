package pdevs.CursITU.service.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdevs.CursITU.controller.request.AssignSubjectDTO;
import pdevs.CursITU.models.SubjectsEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.SubjectsRepo;
import pdevs.CursITU.repositories.UserRepo;

@Service
public class TeacherService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubjectsRepo subjectsRepo;

    @Transactional
    public UserEntity asignarMateriaAProfesor(AssignSubjectDTO dto) {

        // 1. Buscar entidades gestionadas (Profesor)
        UserEntity profesor = userRepo.findById(dto.getProfesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + dto.getProfesorId()));

        // 2. Buscar entidades gestionadas (Materia)
        SubjectsEntity materia = subjectsRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + dto.getMateriaId()));

        // 3. Establecer la Bidireccionalidad (Añadir la relación a ambas entidades)

        // Lado Propietario (UserEntity.materias es la dueña del JoinTable 'materias_profesor')
        profesor.getMaterias().add(materia);

        // Lado Inverso (SubjectsEntity debe tener una colección de profesores mapeada por 'materias')
        materia.getProfesores().add(profesor);

        // 4. Persistir los cambios
        // Al estar en un @Transactional, guardar el lado propietario es suficiente para persistir
        // la relación en la tabla de unión 'materias_profesor'.
        subjectsRepo.save(materia); // Guardar la entidad inversa para asegurar el flush.
        return userRepo.save(profesor);
    }
}

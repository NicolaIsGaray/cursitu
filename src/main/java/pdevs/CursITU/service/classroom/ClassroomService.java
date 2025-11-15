package pdevs.CursITU.service.classroom;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdevs.CursITU.controller.request.CreateClassroomDTO;
import pdevs.CursITU.models.ClassroomEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.ClassroomRepo;
import pdevs.CursITU.repositories.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public ClassroomEntity createClassroomWithStudents(CreateClassroomDTO dto) {

        ClassroomEntity newClassroom = dto.getClassroom();

        Set<UserEntity> incomingStudents = new HashSet<>(newClassroom.getAlumnos());

        newClassroom.setAlumnos(new HashSet<>());

        Set<UserEntity> incomingProfesores = new HashSet<>(newClassroom.getProfesores());

        newClassroom.setProfesores(new HashSet<>());

        ClassroomEntity savedClassroom = classroomRepo.save(newClassroom);

        Set<UserEntity> professorsToSave = new HashSet<>();

        for (UserEntity teacherRef : incomingProfesores) {
            userRepo.findById(teacherRef.getId()).ifPresent(existingTeacher -> {
                savedClassroom.getProfesores().add(existingTeacher);
                existingTeacher.getCursosComoProfesor().add(savedClassroom);
                professorsToSave.add(existingTeacher);
            });
        }

        Set<UserEntity> studentsToSave = new HashSet<>();

        for (UserEntity studentRef : incomingStudents) {

            userRepo.findById(studentRef.getId()).ifPresent(existingStudent -> {
                savedClassroom.getAlumnos().add(existingStudent);
                existingStudent.getCursosComoAlumno().add(savedClassroom);
                studentsToSave.add(existingStudent);
            });
        }

        if (!professorsToSave.isEmpty()) {
            userRepo.saveAll(professorsToSave);
        }

        if (!studentsToSave.isEmpty()) {
            userRepo.saveAll(studentsToSave);
        }

        return classroomRepo.save(savedClassroom);

    }
}

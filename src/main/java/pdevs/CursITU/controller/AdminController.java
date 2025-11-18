package pdevs.CursITU.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import pdevs.CursITU.controller.request.AddUserToClassroomDTO;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.controller.request.AssignSubjectDTO;
import pdevs.CursITU.controller.request.CreateClassroomDTO;
import pdevs.CursITU.models.*;
import pdevs.CursITU.repositories.ClassroomRepo;
import pdevs.CursITU.repositories.RoleRepo;
import pdevs.CursITU.repositories.SubjectsRepo;
import pdevs.CursITU.repositories.UserRepo;
import pdevs.CursITU.service.classroom.ClassroomService;
import pdevs.CursITU.service.user.TeacherService;

import java.util.Map;

@RestController
@RequestMapping("/cursitu-app")
public class AdminController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubjectsRepo subjectsRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private TeacherService teacherService;

    @DeleteMapping("/eliminar-usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable long id) {
        this.userRepo.deleteById(id);
    }

    @PostMapping("/crear-curso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomEntity> addClassroom(@Valid @RequestBody CreateClassroomDTO request) {
        ClassroomEntity savedClassroom = classroomService.createClassroomWithStudents(request);

        return ResponseEntity.ok(savedClassroom);
    }

    @DeleteMapping("/eliminar-curso/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long id) {
        if (!classroomRepo.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con id: " + id);
        }

        classroomService.deleteClassroom(id);
        return ResponseEntity.ok("Curso eliminado exitosamente.");
    }

    @PostMapping("/agregar-materia")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSubject(@Valid @RequestBody SubjectsEntity entity) {
        SubjectsEntity subject = SubjectsEntity.builder()
                .nombre(entity.getNombre())
                .color(entity.getColor())
                .build();

        subjectsRepo.save(subject);

        return ResponseEntity.ok(subject);
    }

    @PostMapping("/asignar-materia")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> assignSubject(@Valid @RequestBody AssignSubjectDTO dto) {
        UserEntity saved = teacherService.asignarMateriaAProfesor(dto);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/eliminar-materia/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSubject(@PathVariable long id) {
        this.subjectsRepo.deleteById(id);
    }

    @PutMapping("/confirmar-rol/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> confirmRequest(@PathVariable long id) {

        UserEntity userRequest = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        userRequest.setRequestForTeacherRole(false);

        RoleEntity rolProfesor = roleRepo.findByRole(ERole.PROFESOR).orElseThrow();
        RoleEntity rolAlumno = roleRepo.findByRole(ERole.ALUMNO).orElseThrow();

        userRequest.getRoles().remove(rolAlumno);
        userRequest.getRoles().add(rolProfesor);

        userRepo.save(userRequest);

        return ResponseEntity.ok(userRequest);
    }

    @PutMapping("/denegar-rol/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> denyRequest(@PathVariable long id) {

        UserEntity userRequest = userRepo.findById(id).orElseThrow();

        userRequest.setRequestForTeacherRole(false);

        userRepo.save(userRequest);

        return ResponseEntity.ok(userRequest);
    }

    @PutMapping("/actualizar-comision/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> updateComision(@PathVariable long id, @RequestBody Map<String, String> payload) {
        UserEntity userToUpdate = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        String nuevaComision = payload.get("comision");

        userToUpdate.setComision(nuevaComision);
        userRepo.save(userToUpdate);

        return ResponseEntity.ok(userToUpdate);
    }

    @PostMapping("/cursos/{classroomId}/agregar-alumno")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomEntity> addStudentToClassroom(@PathVariable Long classroomId, @Valid @RequestBody AddUserToClassroomDTO dto) {
        ClassroomEntity updatedClassroom = classroomService.addStudentToClassroom(classroomId, dto.getUserId());
        return ResponseEntity.ok(updatedClassroom);
    }

    @PostMapping("/cursos/{classroomId}/agregar-profesor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomEntity> addTeacherToClassroom(@PathVariable Long classroomId, @Valid @RequestBody AddUserToClassroomDTO dto) {
        ClassroomEntity updatedClassroom = classroomService.addTeacherToClassroom(classroomId, dto.getUserId());
        return ResponseEntity.ok(updatedClassroom);
    }

    @DeleteMapping("/cursos/{classroomId}/eliminar-profesor/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeTeacherFromClassroom(@PathVariable Long classroomId, @PathVariable Long teacherId) {
        if (!classroomRepo.existsById(classroomId)) {
            throw new RuntimeException("Curso no encontrado con id: " + classroomId);
        }
        if (!userRepo.existsById(teacherId)) {
            throw new RuntimeException("Profesor no encontrado con id: " + teacherId);
        }

        classroomService.removeTeacherFromClassroom(classroomId, teacherId);
        return ResponseEntity.ok("Profesor quitado del curso exitosamente.");
    }

    @DeleteMapping("/cursos/{classroomId}/eliminar-alumno/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeStudentFromClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        if (!classroomRepo.existsById(classroomId)) {
            throw new RuntimeException("Curso no encontrado con id: " + classroomId);
        }
        if (!userRepo.existsById(studentId)) {
            throw new RuntimeException("Alumno no encontrado con id: " + studentId);
        }

        classroomService.removeStudentFromClassroom(classroomId, studentId);
        return ResponseEntity.ok("Alumno quitado del curso exitosamente.");
    }
}

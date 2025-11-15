package pdevs.CursITU.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.controller.request.AssignSubjectDTO;
import pdevs.CursITU.controller.request.CreateClassroomDTO;
import pdevs.CursITU.models.*;
import pdevs.CursITU.repositories.ClassroomRepo;
import pdevs.CursITU.repositories.SubjectsRepo;
import pdevs.CursITU.repositories.UserRepo;
import pdevs.CursITU.service.classroom.ClassroomService;
import pdevs.CursITU.service.user.TeacherService;

import java.util.HashSet;
import java.util.Set;

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

    @PostMapping("/agregar-materia")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSubject(@Valid @RequestBody SubjectsEntity entity) {
        SubjectsEntity subject = SubjectsEntity.builder()
                .nombre(entity.getNombre())
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

        UserEntity userRequest = userRepo.findById(id).orElseThrow();
        RoleEntity nuevoRol = RoleEntity.builder()
                .role(ERole.valueOf(ERole.PROFESOR.name()))
                .build();

        userRequest.setRequestForTeacherRole(false);

        userRequest.getRoles().add(nuevoRol);

        Set<RoleEntity> nuevosRoles = new HashSet<>();
        nuevosRoles.add(nuevoRol);
        userRequest.setRoles(nuevosRoles);

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
}

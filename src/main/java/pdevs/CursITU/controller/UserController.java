package pdevs.CursITU.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.controller.request.CreateUserDTO;
import pdevs.CursITU.models.ERole;
import pdevs.CursITU.models.RoleEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.UserRepo;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "http://localhost:8080/cursitu-app")
@Slf4j
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/accessAdmin")
    @PreAuthorize("hasRole('ALUMNO')")
    public String accessAdmin() {
        return "Hola, has accedido con rol de ALUMNO";
    }

    @PostMapping("/registrar-usuario")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .role(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());
        UserEntity userEntity = UserEntity.builder()
                .nombre(createUserDTO.getNombre())
                .clave(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepo.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestParam String id) {
        userRepo.deleteById(Long.parseLong(id));
        return "Usuario eliminado con id ".concat(id);
    }
}

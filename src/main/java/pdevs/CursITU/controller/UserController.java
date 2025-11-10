package pdevs.CursITU.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}

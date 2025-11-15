package pdevs.CursITU.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.controller.request.CreateUserDTO;
import pdevs.CursITU.models.ERole;
import pdevs.CursITU.models.RoleEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.UserRepo;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursitu-app")
@Slf4j
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user-info/{dni}")
    public UserEntity getUserInfo(@PathVariable String dni) {
        UserEntity user = this.userRepo.findByDni(dni).orElse(null);

        if (user == null) {
            log.info("\nUsuario no encontrado.\n");
        }

        return user;
    }

    @GetMapping("/usuarios")
    public List<UserEntity> getUsers() {
        return (List<UserEntity>) this.userRepo.findAll();
    }

    @GetMapping("/usuario/{id}")
    public UserEntity getUserByID(@PathVariable Long id) {
        return (UserEntity) this.userRepo.findById(id).orElse(null);
    }

    @PostMapping("/registrar-usuario")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .role(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .email(createUserDTO.getEmail())
                .nombre(createUserDTO.getNombre())
                .clave(passwordEncoder.encode(createUserDTO.getClave()))
                .dni(createUserDTO.getDni())
                .roles(roles)
                .build();

        userRepo.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }
}

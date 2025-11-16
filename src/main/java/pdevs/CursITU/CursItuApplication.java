package pdevs.CursITU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pdevs.CursITU.models.*;
import pdevs.CursITU.repositories.RoleRepo;
import pdevs.CursITU.repositories.SubjectsRepo;
import pdevs.CursITU.repositories.UserRepo;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class CursItuApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursItuApplication.class, args);
	}

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubjectsRepo subjectsRepo;

    @Bean
    CommandLineRunner init(RoleRepo roleRepo, UserRepo userRepo, SubjectsRepo subjectsRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Asegurar que todos los roles existen de forma idempotente
            Arrays.stream(ERole.values()).forEach(eRole -> {
                if (roleRepo.findByRole(eRole).isEmpty()) {
                    roleRepo.save(RoleEntity.builder().role(eRole).build());
                }
            });

            // 2. Cargar los roles para reutilizarlos
            RoleEntity adminRole = roleRepo.findByRole(ERole.ADMIN).orElseThrow();
            RoleEntity alumnoRole = roleRepo.findByRole(ERole.ALUMNO).orElseThrow();
            RoleEntity profesorRole = roleRepo.findByRole(ERole.PROFESOR).orElseThrow();

            // 3. Crear usuarios de prueba (de forma idempotente)
            createUserIfNotFound(userRepo, "01010101", "gabidi@gmail.com", "Gabriel Di Cesare", "1234", Set.of(adminRole), false);
            createUserIfNotFound(userRepo, "22222222", "mili@gmail.com", "Milagros Garcia", "4321", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "33333333", "lilenmimi@gmail.com", "Lilen Pereira", "6969", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "44444444", "jorgeperez@gmail.com", "Jorge Perez", "jorge123", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "55555555", "prades@gmail.com", "Mauricio Prades", "prades1234", Set.of(alumnoRole), true);
            createUserIfNotFound(userRepo, "66666666", "joaquindi@gmail.com", "Joaquin Diaz", "joa123", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "77777777", "pablogomez@gmail.com", "Pablo Gomez", "pablo1234", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "88888888", "cristgon@gmail.com", "Cristian Gonzalez", "cris2842", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "99999999", "mary@gmail.com", "Maria Gimenez", "mari231", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "10101010", "pedrol@gmail.com", "Pedro Sanchez", "pedro2123", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "11111111", "abril@gmail.com", "Abril Luna", "abril1142", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "12121212", "sol@gmail.com", "Sol Diaz", "sole24214", Set.of(alumnoRole), false);
            createUserIfNotFound(userRepo, "13131313", "mati@gmail.com", "Matias Liberal", "mati1313", Set.of(profesorRole), false);

            // 4. Crear materias de prueba (de forma idempotente)
            createSubjectIfNotFound(subjectsRepo, "Sistemas Operativos Avanzados", "#DB2E09");
            createSubjectIfNotFound(subjectsRepo, "Bases de Datos Avanzadas", "#A6B244");
            createSubjectIfNotFound(subjectsRepo, "Metodología y Testing", "#6144B2");
            createSubjectIfNotFound(subjectsRepo, "Lógica Matemática", "#12A654");
            createSubjectIfNotFound(subjectsRepo, "Diseño de Software", "#122BA6");
            createSubjectIfNotFound(subjectsRepo, "Interpretación de textos en inglés", "#CF069C");
        };
    }

    private void createUserIfNotFound(UserRepo userRepo, String dni, String email, String nombre, String clave, Set<RoleEntity> roles, boolean requestForTeacherRole) {
        if (userRepo.findByDni(dni).isEmpty()) {
            UserEntity user = UserEntity.builder()
                    .dni(dni)
                    .email(email)
                    .nombre(nombre)
                    .clave(passwordEncoder.encode(clave))
                    .roles(roles)
                    .requestForTeacherRole(requestForTeacherRole)
                    .build();
            userRepo.save(user);
        }
    }

    private void createSubjectIfNotFound(SubjectsRepo subjectsRepo, String nombre, String color) {
        if (subjectsRepo.findByNombre(nombre).isEmpty()) {
            SubjectsEntity subject = SubjectsEntity.builder()
                    .nombre(nombre)
                    .color(color)
                    .build();
            subjectsRepo.save(subject);
        }
    }
}

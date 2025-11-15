package pdevs.CursITU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pdevs.CursITU.models.*;
import pdevs.CursITU.repositories.SubjectsRepo;
import pdevs.CursITU.repositories.UserRepo;

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
    CommandLineRunner init() {
        return args -> {
            UserEntity userEntity = UserEntity.builder()
                    .email("gabidi@gmail.com")
                    .nombre("Gabriel Di Cesare")
                    .clave(passwordEncoder.encode("1234"))
                    .dni("01010101")
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ADMIN.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity2 = UserEntity.builder()
                    .email("mili@gmail.com")
                    .nombre("Milagros Garcia")
                    .dni("22222222")
                    .clave(passwordEncoder.encode("4321"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity3 = UserEntity.builder()
                    .email("lilenmimi@gmail.com")
                    .nombre("Lilen Pereira")
                    .dni("33333333")
                    .clave(passwordEncoder.encode("6969"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity4 = UserEntity.builder()
                    .email("jorgeperez@gmail.com")
                    .nombre("Jorge Perez")
                    .dni("44444444")
                    .clave(passwordEncoder.encode("jorge123"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity5 = UserEntity.builder()
                    .email("prades@gmail.com")
                    .nombre("Mauricio Prades")
                    .dni("55555555")
                    .clave(passwordEncoder.encode("prades1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .requestForTeacherRole(true)
                    .build();

            UserEntity userEntity6 = UserEntity.builder()
                    .email("joaquindi@gmail.com")
                    .nombre("Joaquin Diaz")
                    .dni("66666666")
                    .clave(passwordEncoder.encode("joa123"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity7 = UserEntity.builder()
                    .email("pablogomez@gmail.com")
                    .nombre("Pablo Gomez")
                    .dni("77777777")
                    .clave(passwordEncoder.encode("pablo1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity8 = UserEntity.builder()
                    .email("cristgon@gmail.com")
                    .nombre("Cristian Gonzalez")
                    .dni("88888888")
                    .clave(passwordEncoder.encode("cris2842"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.A)
                    .build();

            UserEntity userEntity9 = UserEntity.builder()
                    .email("mary@gmail.com")
                    .nombre("Maria Gimenez")
                    .dni("99999999")
                    .clave(passwordEncoder.encode("mari231"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.B)
                    .build();

            UserEntity userEntity10 = UserEntity.builder()
                    .email("pedrol@gmail.com")
                    .nombre("Pedro Sanchez")
                    .dni("10101010")
                    .clave(passwordEncoder.encode("pedro2123"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.B)
                    .build();

            UserEntity userEntity11 = UserEntity.builder()
                    .email("abril@gmail.com")
                    .nombre("Abril Luna")
                    .dni("11111111")
                    .clave(passwordEncoder.encode("abril1142"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.B)
                    .build();

            UserEntity userEntity12 = UserEntity.builder()
                    .email("sol@gmail.com")
                    .nombre("Sol Diaz")
                    .dni("12121212")
                    .clave(passwordEncoder.encode("sole24214"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.ALUMNO.name()))
                            .build()
                    ))
                    .comision(EComission.B)
                    .build();

            UserEntity userEntity13 = UserEntity.builder()
                    .email("mati@gmail.com")
                    .nombre("Matias")
                    .dni("13131313")
                    .clave(passwordEncoder.encode("mati1313"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(ERole.valueOf(ERole.PROFESOR.name()))
                            .build()
                    ))
                    .build();

            userRepo.save(userEntity);
            userRepo.save(userEntity2);
            userRepo.save(userEntity3);
            userRepo.save(userEntity4);
            userRepo.save(userEntity5);
            userRepo.save(userEntity6);
            userRepo.save(userEntity7);
            userRepo.save(userEntity8);
            userRepo.save(userEntity9);
            userRepo.save(userEntity10);
            userRepo.save(userEntity11);
            userRepo.save(userEntity12);
            userRepo.save(userEntity13);


            SubjectsEntity subject1 = SubjectsEntity.builder()
                    .nombre("Sistemas Operativos Avanzados")
                    .color("#DB2E09")
                    .year("2026")
                    .build();

            SubjectsEntity subject2 = SubjectsEntity.builder()
                    .nombre("Bases de Datos Avanzadas")
                    .color("#A6B244")
                    .year("2026")
                    .build();

            SubjectsEntity subject3 = SubjectsEntity.builder()
                    .nombre("Metodología y Testing")
                    .color("#6144B2")
                    .year("2026")
                    .build();

            SubjectsEntity subject4 = SubjectsEntity.builder()
                    .nombre("Lógica Matemática")
                    .color("#12A654")
                    .year("2026")
                    .build();

            SubjectsEntity subject5 = SubjectsEntity.builder()
                    .nombre("Diseño de Software")
                    .color("#122BA6")
                    .year("2026")
                    .build();

            SubjectsEntity subject6 = SubjectsEntity.builder()
                    .nombre("Interpretación de textos en inglés")
                    .color("#CF069C")
                    .year("2026")
                    .build();

            subjectsRepo.save(subject1);
            subjectsRepo.save(subject2);
            subjectsRepo.save(subject3);
            subjectsRepo.save(subject4);
            subjectsRepo.save(subject5);
            subjectsRepo.save(subject6);
        };
    }
}

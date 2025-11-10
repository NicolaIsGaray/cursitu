package pdevs.CursITU.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "grupo")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 20)
    private String nombre;

    @NotBlank
    @Size(max = 6)
    private int limite;

    @ManyToOne
    @JoinColumn(name = "DNIProfesor", nullable = false)
    private UserEntity profesorAdministrador;

    @ManyToOne
    @JoinColumn(name = "IDCurso", nullable = false)
    private ClassroomEntity cursoPerteneciente;

    @ManyToMany
    @JoinTable(
            name = "GrupoIntegraAlumnos",
            joinColumns = @JoinColumn(name = "IDGrupo"),
            inverseJoinColumns = @JoinColumn(name = "DNIAlumno")
    )
    private Set<UserEntity> alumnosIntegrantes;
}

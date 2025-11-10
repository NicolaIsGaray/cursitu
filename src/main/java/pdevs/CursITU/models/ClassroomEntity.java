package pdevs.CursITU.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "curso")
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String year;

    @NotBlank
    private String comision;

    @NotBlank
    private String materia;

    @ManyToOne
    @JoinColumn(name = "DNIProfesor", nullable = false)
    private UserEntity profesor;

    @OneToMany(mappedBy = "cursoPerteneciente")
    private Set<GroupEntity> grupos;

    @ManyToMany
    @JoinTable(
            name = "CursoCompuestoAlumnos",
            joinColumns = @JoinColumn(name = "IDCurso"),
            inverseJoinColumns = @JoinColumn(name = "DNIAlumno")
    )
    private Set<UserEntity> alumnosInscritos;
}

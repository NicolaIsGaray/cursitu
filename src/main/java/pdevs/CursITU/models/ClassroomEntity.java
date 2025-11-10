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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = UserEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "curso_compuesto",
    joinColumns = @JoinColumn(name = "curso_id"),
    inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<UserEntity> integrantes;
}

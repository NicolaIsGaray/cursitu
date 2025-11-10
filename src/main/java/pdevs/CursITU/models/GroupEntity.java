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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = UserEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "grupo_alumnos",
        joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<UserEntity> miembros;
}

package pdevs.CursITU.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "materias")
public class SubjectsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private String color;

    private String year;

    private String comision;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "materias", fetch = FetchType.LAZY)
    private Set<UserEntity> profesores = new HashSet<>();
}

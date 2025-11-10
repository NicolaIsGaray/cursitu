package pdevs.CursITU.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 60)
    private String nombre;

    @NotBlank
    private String clave;

    @NotBlank
    @Size(max = 8)
    private long dni;

    @OneToMany(mappedBy = "profesor")
    private Set<ClassroomEntity> cursosDictados;

    @OneToMany(mappedBy = "profesorAdministrador")
    private Set<GroupEntity> gruposAdministrados;

    @ManyToMany(mappedBy = "alumnosInscritos")
    private Set<ClassroomEntity> cursosCompuestos;

    @ManyToMany(mappedBy = "alumnosIntegrantes")
    private Set<GroupEntity> gruposIntegrados;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuario_roles",
    joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleEntity> roles;
}

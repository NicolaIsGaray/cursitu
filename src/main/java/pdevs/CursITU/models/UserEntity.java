package pdevs.CursITU.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    private boolean isActive;

    private boolean hasGroup = false;

    @EqualsAndHashCode.Exclude
    @JsonBackReference("classroom-student")
    @ManyToMany(mappedBy = "alumnos", fetch = FetchType.LAZY)
    private Set<ClassroomEntity> cursosComoAlumno = new HashSet<>();

    @JsonBackReference("classroom-teacher")
    @ManyToMany(mappedBy = "profesores", fetch = FetchType.LAZY)
    private Set<ClassroomEntity> cursosComoProfesor = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = SubjectsEntity.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "materias_profesor",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "materias_id"))
    private Set<SubjectsEntity> materias;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    private String comision;

    private boolean requestForTeacherRole = false;

    @OneToMany(mappedBy = "invitedUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<InvitationsEntity> invitaciones = new HashSet<>();
}

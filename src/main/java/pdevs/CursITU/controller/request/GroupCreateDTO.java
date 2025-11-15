package pdevs.CursITU.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateDTO {
    private String nombre;
    private int limite;
    private String comision;

    private Set<Long> miembrosIds;
    private Set<Long> pendientesIds;
}

package pdevs.CursITU.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUserToClassroomDTO {
    @NotNull
    private Long userId;
}
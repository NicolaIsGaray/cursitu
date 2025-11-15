package pdevs.CursITU.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pdevs.CursITU.models.ClassroomEntity;
import pdevs.CursITU.models.UserEntity;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClassroomDTO {

    private ClassroomEntity classroom;

    private Set<UserEntity> students;
}

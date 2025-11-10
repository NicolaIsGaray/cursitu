package pdevs.CursITU.repositories;

import org.springframework.data.repository.CrudRepository;
import pdevs.CursITU.models.ClassroomEntity;

public interface ClassroomRepo extends CrudRepository<ClassroomEntity, Long> {
}

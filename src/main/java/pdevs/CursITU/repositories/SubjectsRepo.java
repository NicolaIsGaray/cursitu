package pdevs.CursITU.repositories;

import org.springframework.data.repository.CrudRepository;
import pdevs.CursITU.models.SubjectsEntity;

import java.util.Optional;

public interface SubjectsRepo extends CrudRepository<SubjectsEntity, Long> {
    Optional<SubjectsEntity> findByNombre(String nombre);
}

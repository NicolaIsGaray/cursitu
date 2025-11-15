package pdevs.CursITU.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pdevs.CursITU.models.UserEntity;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByDni(String dni);

    Optional<UserEntity> findAllById(Long id);

    Optional<UserEntity> findByNombre(String nombre);
}

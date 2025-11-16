package pdevs.CursITU.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pdevs.CursITU.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByDni(String dni);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"invitaciones", "roles"})
    List<UserEntity> findAll();

    Optional<UserEntity> findAllById(Long id);

    Optional<UserEntity> findByNombre(String nombre);
}

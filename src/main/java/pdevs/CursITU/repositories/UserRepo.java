package pdevs.CursITU.repositories;

import org.springframework.data.repository.CrudRepository;
import pdevs.CursITU.models.UserEntity;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByDni(Long dni);
}

package pdevs.CursITU.repositories;

import org.springframework.data.repository.CrudRepository;
import pdevs.CursITU.models.ERole;
import pdevs.CursITU.models.RoleEntity;
import pdevs.CursITU.models.UserEntity;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(ERole role);
}

package pdevs.CursITU.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdevs.CursITU.models.ERole;
import pdevs.CursITU.models.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(ERole role);
}
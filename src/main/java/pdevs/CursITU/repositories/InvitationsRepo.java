package pdevs.CursITU.repositories;

import org.springframework.data.repository.CrudRepository;
import pdevs.CursITU.models.InvitationsEntity;
import pdevs.CursITU.models.UserEntity;

import java.util.Optional;

public interface InvitationsRepo extends CrudRepository<InvitationsEntity, Long> {
    Optional<InvitationsEntity> findByInvitedUserAndGroupId(UserEntity user, Long groupID);
}

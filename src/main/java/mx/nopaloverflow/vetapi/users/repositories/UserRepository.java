package mx.nopaloverflow.vetapi.users.repositories;

import mx.nopaloverflow.vetapi.core.repositories.VetCrudRepository;
import mx.nopaloverflow.vetapi.users.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends VetCrudRepository<UserEntity> {
    Optional<UserEntity> findByEmail(final String email);
}

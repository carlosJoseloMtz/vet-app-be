package mx.nopaloverflow.vetapi.core.repositories;

import java.util.Optional;

public interface VetCrudRepository<EntityType> {
    Long create(EntityType entity);

    Optional<EntityType> findById(final Long id);

    boolean isItemInDB(final Long id);

    void update(EntityType entity);
}

package mx.nopaloverflow.vetapi.customers.repositories;

import mx.nopaloverflow.vetapi.core.repositories.VetCrudRepository;
import mx.nopaloverflow.vetapi.customers.entities.PetEntity;

import java.util.List;

public interface PetRepository extends VetCrudRepository<PetEntity> {

    List<PetEntity> findAllByOwner(final Long petOwnerId);
}

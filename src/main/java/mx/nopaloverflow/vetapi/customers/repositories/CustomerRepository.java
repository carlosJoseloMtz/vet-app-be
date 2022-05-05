package mx.nopaloverflow.vetapi.customers.repositories;

import mx.nopaloverflow.vetapi.core.repositories.VetCrudRepository;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends VetCrudRepository<CustomerEntity> {
    Optional<CustomerEntity> findByEmail(final String email);
}

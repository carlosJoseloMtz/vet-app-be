package mx.nopaloverflow.vetapi.customers.repositories.impl;

import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.exceptions.SystemException;
import mx.nopaloverflow.vetapi.core.repositories.AbstractRepository;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.repositories.CustomerRepository;

import java.sql.SQLException;
import java.util.Optional;

public class DefaultCustomerRepository extends AbstractRepository<CustomerEntity> implements CustomerRepository {

    public DefaultCustomerRepository(final ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    protected Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }

    @Override
    public Optional<CustomerEntity> findByEmail(final String email) {
        try {
            final var results = getEntityDao().queryForEq("email", email);

            if (results.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(results.get(0));
        } catch (SQLException ex) {
            throw new SystemException("Error while fetching customer by email", ex);
        }
    }
}

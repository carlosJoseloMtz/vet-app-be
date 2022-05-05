package mx.nopaloverflow.vetapi.customers.strategies.impl;

import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.exceptions.SystemException;
import mx.nopaloverflow.vetapi.core.repositories.AbstractRepository;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.handlers.requests.CustomerSearchTerms;
import mx.nopaloverflow.vetapi.customers.strategies.CustomerSearchStrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the customer search using like statements and hits the DB directly.
 * This is a non performant way of doing it, however, for the first iteration it's fine
 */
public class DBBasedCustomerSearchStrategy extends AbstractRepository<CustomerEntity> implements CustomerSearchStrategy {

    public DBBasedCustomerSearchStrategy(final ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    public List<CustomerEntity> search(final CustomerSearchTerms searchTerms) {
        try {
            return executeQuery(searchTerms);
        } catch (final SQLException ex) {
            throw new SystemException("Error when performing the customer search operation", ex);
        }
    }

    private boolean isEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    private String getOrEmpty(final String text) {
        return text == null ? "" : text.trim().toLowerCase();
    }

    private String wrap(final String value) {
        return "%" + value + "%";
    }

    private List<CustomerEntity> executeQuery(final CustomerSearchTerms searchTerms) throws SQLException {
        var email = getOrEmpty(searchTerms.getEmail());
        var name = getOrEmpty(searchTerms.getName());
        var phoneNumber = getOrEmpty(searchTerms.getPhone());

        final var isEmailEmpty = isEmpty(email);
        final var isNameEmpty = isEmpty(name);
        final var isPhoneEmpty = isEmpty(phoneNumber);

        if (isEmailEmpty && isNameEmpty && isPhoneEmpty) {
            return getEntityDao().queryForAll();
        }

        email = wrap(email);
        name = wrap(name);
        phoneNumber = wrap(phoneNumber);

        final var filters = new ArrayList<String>();
        final var values = new ArrayList<String>();

        if (!isEmailEmpty) {
            filters.add("lower(email) like ?");
            values.add(email);
        }

        if (!isNameEmpty) {
            filters.add("lower(name) like ?");
            values.add(name);
        }

        if (!isPhoneEmpty) {
            filters.add("phone_number like ?");
            values.add(phoneNumber);
        }

        final var results = getEntityDao().queryRaw("select * from customers where " +
                        String.join(" and ", filters),
                getEntityDao().getRawRowMapper(),
                values.toArray(new String[0]));

        return results.getResults();
    }

    @Override
    protected Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }
}

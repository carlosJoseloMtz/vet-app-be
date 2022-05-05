package mx.nopaloverflow.vetapi.customers.strategies;

import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.handlers.requests.CustomerSearchTerms;

import java.util.List;

public interface CustomerSearchStrategy {
    List<CustomerEntity> search(final CustomerSearchTerms searchTerms);
}

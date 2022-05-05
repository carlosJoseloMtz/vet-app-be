package mx.nopaloverflow.vetapi.customers.services;

import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.handlers.requests.CustomerSearchTerms;

import java.util.List;

public interface CustomerSearchService {

    List<CustomerDto> search(final CustomerSearchTerms searchTerms);
}

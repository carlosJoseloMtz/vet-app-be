package mx.nopaloverflow.vetapi.customers.services;

import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.exceptions.CustomerAlreadyExistsException;

public interface CustomerService {

    Long registerCustomer(final CustomerDto customer) throws CustomerAlreadyExistsException;

    void updateCustomer(final Long id, final CustomerDto customer);

}

package mx.nopaloverflow.vetapi.customers.services.impl;

import mx.nopaloverflow.vetapi.core.exceptions.EntityNotFoundException;
import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.exceptions.CustomerAlreadyExistsException;
import mx.nopaloverflow.vetapi.customers.repositories.CustomerRepository;
import mx.nopaloverflow.vetapi.customers.services.CustomerService;
import org.modelmapper.ModelMapper;

public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper customersModelMapper;

    public DefaultCustomerService(final CustomerRepository customerRepository,
                                  final ModelMapper customersModelMapper) {
        this.customerRepository = customerRepository;
        this.customersModelMapper = customersModelMapper;
    }

    @Override
    public Long registerCustomer(final CustomerDto customer) throws CustomerAlreadyExistsException {
        final var email = customer.getEmail();
        final var existingCustomer = customerRepository.findByEmail(email);

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(
                    String.format("Customer [%s] is already registered.", email));
        }

        final var customerEntity = customersModelMapper.map(customer, CustomerEntity.class);
        return customerRepository.create(customerEntity);
    }

    @Override
    public void updateCustomer(final Long id, final CustomerDto customer) {
        final var pk = customerRepository.findById(id)
                .map(CustomerEntity::getEntityPk)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Customer [%d] is not registered into the system", id)));

        final var updatedEntity = customersModelMapper.map(customer, CustomerEntity.class);
        updatedEntity.setEntityPk(pk);

        customerRepository.update(updatedEntity);
    }
}

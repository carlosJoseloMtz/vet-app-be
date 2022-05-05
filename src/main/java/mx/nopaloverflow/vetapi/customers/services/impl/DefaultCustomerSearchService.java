package mx.nopaloverflow.vetapi.customers.services.impl;

import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.handlers.requests.CustomerSearchTerms;
import mx.nopaloverflow.vetapi.customers.services.CustomerSearchService;
import mx.nopaloverflow.vetapi.customers.strategies.CustomerSearchStrategy;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCustomerSearchService implements CustomerSearchService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCustomerSearchService.class);

    private final CustomerSearchStrategy customerSearchStrategy;
    private final ModelMapper modelMapper;

    public DefaultCustomerSearchService(final CustomerSearchStrategy customerSearchStrategy,
                                        final ModelMapper modelMapper) {
        this.customerSearchStrategy = customerSearchStrategy;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDto> search(final CustomerSearchTerms searchTerms) {
        try {
            return customerSearchStrategy.search(searchTerms).stream()
                    .map(this::mapSafely)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (final Throwable ex) {
            LOG.error("Error while trying to search for a customer", ex);
        }

        return Collections.emptyList();
    }

    protected CustomerDto mapSafely(final CustomerEntity entity) {
        try {
            return modelMapper.map(entity, CustomerDto.class);
        } catch (final Throwable ex) {
            LOG.warn("Error while trying to parse a customer entity to a customer dto");
        }

        return null;
    }
}

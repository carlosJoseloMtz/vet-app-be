package mx.nopaloverflow.vetapi.customers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.dtos.PetDto;
import mx.nopaloverflow.vetapi.customers.entities.CustomerEntity;
import mx.nopaloverflow.vetapi.customers.entities.PetEntity;
import mx.nopaloverflow.vetapi.customers.handlers.CustomerHandler;
import mx.nopaloverflow.vetapi.customers.handlers.PetHandler;
import mx.nopaloverflow.vetapi.customers.repositories.CustomerRepository;
import mx.nopaloverflow.vetapi.customers.repositories.PetRepository;
import mx.nopaloverflow.vetapi.customers.repositories.impl.DefaultCustomerRepository;
import mx.nopaloverflow.vetapi.customers.repositories.impl.DefaultPetRepository;
import mx.nopaloverflow.vetapi.customers.services.CustomerSearchService;
import mx.nopaloverflow.vetapi.customers.services.CustomerService;
import mx.nopaloverflow.vetapi.customers.services.PetService;
import mx.nopaloverflow.vetapi.customers.services.impl.DefaultCustomerSearchService;
import mx.nopaloverflow.vetapi.customers.services.impl.DefaultCustomerService;
import mx.nopaloverflow.vetapi.customers.services.impl.DefaultPetService;
import mx.nopaloverflow.vetapi.customers.strategies.CustomerSearchStrategy;
import mx.nopaloverflow.vetapi.customers.strategies.impl.DBBasedCustomerSearchStrategy;
import org.modelmapper.ModelMapper;

public class CustomersModule extends AbstractModule {


    @Provides
    @Named("customersModelMapper")
    ModelMapper customersModelMapper() {
        final var mapper = new ModelMapper();

        mapper.typeMap(CustomerEntity.class, CustomerDto.class)
                .addMapping(CustomerEntity::getEntityPk, CustomerDto::setId);
        mapper.typeMap(PetEntity.class, PetDto.class)
                .addMapping(PetEntity::getEntityPk, PetDto::setId);

        return mapper;
    }

    @Provides
    @Named("customerRepository")
    @Singleton
    CustomerRepository customerRepository(@Named("connectionSource") final ConnectionSource connectionSource) {
        return new DefaultCustomerRepository(connectionSource);
    }

    @Provides
    @Named("customerService")
    CustomerService customerService(@Named("customerRepository") final CustomerRepository customerRepository,
                                    @Named("customersModelMapper") final ModelMapper customersModelMapper) {
        return new DefaultCustomerService(customerRepository, customersModelMapper);
    }

    @Provides
    @Named("petRepository")
    PetRepository petRepository(@Named("connectionSource") final ConnectionSource connectionSource) {
        return new DefaultPetRepository(connectionSource);
    }

    @Provides
    @Named("petService")
    PetService petService(@Named("customerRepository") final CustomerRepository customerRepository,
                          @Named("petRepository") final PetRepository petRepository,
                          @Named("customersModelMapper") final ModelMapper mapper) {
        return new DefaultPetService(customerRepository, petRepository, mapper);
    }

    @Provides
    @Named("customerSearchStrategy")
    CustomerSearchStrategy customerSearchStrategy(
            @Named("connectionSource") final ConnectionSource connectionSource) {
        return new DBBasedCustomerSearchStrategy(connectionSource);
    }

    @Provides
    @Named("customerSearchService")
    CustomerSearchService customerSearchService(
            @Named("customerSearchStrategy") final CustomerSearchStrategy customerSearchStrategy,
            @Named("customersModelMapper") final ModelMapper modelMapper) {
        return new DefaultCustomerSearchService(customerSearchStrategy, modelMapper);
    }

    @Provides
    @Named("customersHandler")
    CustomerHandler customersHandler(@Named("customerService") final CustomerService customerService,
                                     @Named("customerSearchService") final CustomerSearchService customerSearchService) {
        return new CustomerHandler(customerService, customerSearchService);
    }

    @Provides
    @Named("petHandler")
    PetHandler petHandler(@Named("petService") final PetService petService) {
        return new PetHandler(petService);
    }
}

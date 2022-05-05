package mx.nopaloverflow.vetapi.customers.services.impl;

import mx.nopaloverflow.vetapi.core.exceptions.EntityNotFoundException;
import mx.nopaloverflow.vetapi.customers.dtos.PetDto;
import mx.nopaloverflow.vetapi.customers.entities.PetEntity;
import mx.nopaloverflow.vetapi.customers.repositories.CustomerRepository;
import mx.nopaloverflow.vetapi.customers.repositories.PetRepository;
import mx.nopaloverflow.vetapi.customers.services.PetService;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPetService implements PetService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final ModelMapper mapper;

    public DefaultPetService(final CustomerRepository customerRepository,
                             final PetRepository petRepository,
                             final ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.mapper = mapper;
    }

    @Override
    public Long addPetToCustomer(final Long customerId, final PetDto pet) {
        final var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id [" +
                        customerId + "] does not exist"));

        final var newPet = mapper.map(pet, PetEntity.class);
        newPet.setPetOwner(customer);

        return petRepository.create(newPet);
    }

    @Override
    public List<PetDto> getAllPetsForCustomer(final Long customerId) {
        final var pets = petRepository.findAllByOwner(customerId);

        if (pets == null || pets.isEmpty()) {
            return Collections.emptyList();
        }

        return pets.stream()
                .map(p ->
                        mapper.map(p, PetDto.class))
                .collect(Collectors.toList());
    }
}

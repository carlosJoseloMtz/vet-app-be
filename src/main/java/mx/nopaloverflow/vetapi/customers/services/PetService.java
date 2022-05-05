package mx.nopaloverflow.vetapi.customers.services;

import mx.nopaloverflow.vetapi.customers.dtos.PetDto;

import java.util.List;

public interface PetService {
    Long addPetToCustomer(final Long customerId, final PetDto pet);

    List<PetDto> getAllPetsForCustomer(final Long customerId);
}

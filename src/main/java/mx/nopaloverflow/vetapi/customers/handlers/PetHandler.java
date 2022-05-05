package mx.nopaloverflow.vetapi.customers.handlers;

import io.javalin.http.Context;
import mx.nopaloverflow.vetapi.core.dtos.ServiceResponse;
import mx.nopaloverflow.vetapi.customers.dtos.PetDto;
import mx.nopaloverflow.vetapi.customers.services.PetService;

public class PetHandler {

    private final PetService petService;

    public PetHandler(final PetService petService) {
        this.petService = petService;
    }

    public void createPetForCustomer(final Context ctx) {
        final var customerId = ctx.pathParamAsClass("customerId", Long.class);
        final var pet = ctx.bodyAsClass(PetDto.class);

        final var petId = petService.addPetToCustomer(customerId.get(), pet);

        ctx.status(201)
                .json(new ServiceResponse<>(petId));
    }

    public void listPetsForCustomer(final Context ctx) {
        final var customerId = ctx.pathParamAsClass("customerId", Long.class);

        final var pets = petService.getAllPetsForCustomer(customerId.get());

        ctx.json(pets);
    }
}

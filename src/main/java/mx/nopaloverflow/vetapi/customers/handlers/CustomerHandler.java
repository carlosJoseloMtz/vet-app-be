package mx.nopaloverflow.vetapi.customers.handlers;

import io.javalin.http.Context;
import mx.nopaloverflow.vetapi.core.dtos.ServiceFailedResponse;
import mx.nopaloverflow.vetapi.core.dtos.ServiceResponse;
import mx.nopaloverflow.vetapi.customers.dtos.CustomerDto;
import mx.nopaloverflow.vetapi.customers.exceptions.CustomerAlreadyExistsException;
import mx.nopaloverflow.vetapi.customers.handlers.requests.CustomerSearchTerms;
import mx.nopaloverflow.vetapi.customers.services.CustomerSearchService;
import mx.nopaloverflow.vetapi.customers.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerHandler.class);

    private final CustomerService customerService;
    private final CustomerSearchService customerSearchService;

    public CustomerHandler(final CustomerService customerService,
                           final CustomerSearchService customerSearchService) {
        this.customerService = customerService;
        this.customerSearchService = customerSearchService;
    }

    public void createCustomer(final Context ctx) {
        final var customer = ctx.bodyAsClass(CustomerDto.class);

        try {
            final var id = customerService.registerCustomer(customer);
            ctx.status(201)
                    .json(new ServiceResponse<>(id));
        } catch (CustomerAlreadyExistsException ex) {
            LOG.warn("Customer [{}] is already registered", customer.getEmail());
            ctx.status(400)
                    .json(new ServiceFailedResponse<>(
                            String.format("Customer [%s] is already registered", customer.getEmail())));
        }

    }

    public void updateCustomer(final Context ctx) {
        final var customer = ctx.bodyAsClass(CustomerDto.class);
        final var id = ctx.pathParamAsClass("customerId", Long.class);

        customerService.updateCustomer(id.get(), customer);
        ctx.json(new ServiceResponse<>("Entity successfully updated"));
    }

    public void searchCustomer(final Context ctx) {
        final var name = ctx.queryParamAsClass("name", String.class);
        final var email = ctx.queryParamAsClass("email", String.class);
        final var phone = ctx.queryParamAsClass("phone", String.class);

        final var searchTerms = new CustomerSearchTerms(name.get(), email.get(), phone.get());
        final var results = customerSearchService.search(searchTerms);
        ctx.json(new ServiceResponse<>(results));
    }
}

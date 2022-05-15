package mx.nopaloverflow.vetapi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import io.javalin.Javalin;
import mx.nopaloverflow.vetapi.core.CoreModule;
import mx.nopaloverflow.vetapi.core.dtos.ServiceFailedResponse;
import mx.nopaloverflow.vetapi.core.exceptions.EntityNotFoundException;
import mx.nopaloverflow.vetapi.core.exceptions.SystemException;
import mx.nopaloverflow.vetapi.customers.CustomersModule;
import mx.nopaloverflow.vetapi.customers.handlers.CustomerHandler;
import mx.nopaloverflow.vetapi.customers.handlers.PetHandler;
import mx.nopaloverflow.vetapi.users.UsersModule;
import mx.nopaloverflow.vetapi.users.handlers.UsersHandler;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.inject.name.Names.named;
import static java.util.Optional.ofNullable;


public class VetApplication {
    private static final Logger LOG = LoggerFactory.getLogger(VetApplication.class);

    private static final int DEFAULT_PORT = 3030;
    private static final int STATUS_SERVER_ERROR = 500;
    private static final int STATUS_NOT_FOUND_ERROR = 404;

    private static int getPort() {
        return ofNullable(System.getenv("APP_PORT"))
                .map(Integer::parseInt)
                .orElse(DEFAULT_PORT);
    }

    public static void main(final String[] args) {
        final var injector = Guice.createInjector(
                new CoreModule(),
                new UsersModule(),
                new CustomersModule());

        final var appServer = Javalin.create(conf -> {
                    // TODO: change this for only the given origin from the FE
                    conf.enableCorsForAllOrigins();
                    conf.defaultContentType = "application/json";
                })
                .start(getPort());

        appServer.exception(EntityNotFoundException.class, (ex, ctx) -> {
            LOG.error("Unable to find an entity - [{}]", ex.getMessage(), ex);
            ctx.status(STATUS_NOT_FOUND_ERROR)
                    .json(new ServiceFailedResponse<>("Unable to find entity with the given id"));
        });

        appServer.exception(SystemException.class, (ex, ctx) -> {
            LOG.error("Error with the DB transaction", ex);

            Throwable cause = ex;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }

            ctx.status(STATUS_SERVER_ERROR)
                    .json(new ServiceFailedResponse<>("Error during the transaction: " + cause.getMessage()));
        });

        appServer.exception(RuntimeException.class, (ex, ctx) -> {
            LOG.error("Unhandled exception - [{}]", ex.getMessage(), ex);
            ctx.status(STATUS_SERVER_ERROR)
                    .json(new ServiceFailedResponse<>("Unexpected error. Please contact your system administrator."));
        });

        appServer.exception(Exception.class, (ex, ctx) -> {
            LOG.error("Unhandled exception - [{}]", ex.getMessage(), ex);
            ctx.status(STATUS_SERVER_ERROR)
                    .json(new ServiceFailedResponse<>("Unexpected error. Please contact your system administrator."));
        });

        // import all the routes

        bindUsersAPI(appServer, injector);
        bindCustomersAPI(appServer, injector);
    }

    // TODO: most likely this will be injected per module
    static void bindUsersAPI(final Javalin app, final Injector injector) {
        final var usersHandler = injector.getInstance(
                Key.get(UsersHandler.class, named("usersHandler")));
        app.post("/authenticate", usersHandler::login);
        app.post("/users", usersHandler::registerUser);
        app.put("/users/{userId}", usersHandler::updateUser);
    }

    static void bindCustomersAPI(final Javalin app, final Injector injector) {
        final var customersHandler = injector.getInstance(
                Key.get(CustomerHandler.class, named("customersHandler")));
        app.post("/customers", customersHandler::createCustomer);
        app.put("/customers/{customerId}", customersHandler::updateCustomer);
        app.get("/customers/search", customersHandler::searchCustomer);

        final var petHandler = injector.getInstance(
                Key.get(PetHandler.class, named("petHandler")));
        app.post("/customers/{customerId}/pets", petHandler::createPetForCustomer);
        app.get("/customers/{customerId}/pets", petHandler::listPetsForCustomer);
    }

}

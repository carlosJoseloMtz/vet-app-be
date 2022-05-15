package mx.nopaloverflow.vetapi.users.handlers;

import io.javalin.http.Context;
import mx.nopaloverflow.vetapi.core.dtos.ServiceFailedResponse;
import mx.nopaloverflow.vetapi.core.dtos.ServiceResponse;
import mx.nopaloverflow.vetapi.users.exceptions.UserAlreadyRegisteredException;
import mx.nopaloverflow.vetapi.users.handlers.requests.LoginRequest;
import mx.nopaloverflow.vetapi.users.dtos.UserDto;
import mx.nopaloverflow.vetapi.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UsersHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UsersHandler.class);

    private final UserService userService;

    public UsersHandler(
            final UserService userService) {
        this.userService = userService;
    }

    public void registerUser(final Context ctx) {
        final var user = ctx.bodyAsClass(UserDto.class);

        try {
            final var id = userService.registerUser(user);

            ctx.status(201)
                    .json(new ServiceResponse<>(id));
        } catch (final UserAlreadyRegisteredException e) {
            ctx.status(500)
                    .json(new ServiceFailedResponse<>("Unable to create user"));
        }

    }

    public void login(final Context ctx) {
        final var credentials = ctx.bodyAsClass(LoginRequest.class);

        try {
            final var userInfo = userService.authenticateUser(
                    credentials.getUsername(),
                    credentials.getPassword());

            ctx.json(new ServiceResponse<>(userInfo));
        } catch (final Throwable ex) {
            LOG.error("Error while logging in", ex);
            ctx.json(new ServiceFailedResponse<>("Username or password incorrect"));
        }
    }

    public void updateUser(final Context ctx) {
        final var user = ctx.bodyAsClass(UserDto.class);
        final var id = ctx.pathParamAsClass("userId", Long.class);

        userService.updateUser(id.get(), user);
        ctx.json(new ServiceResponse<>("Entity successfully updated"));
    }
}

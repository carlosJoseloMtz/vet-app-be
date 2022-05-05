package mx.nopaloverflow.vetapi.users.services;

import mx.nopaloverflow.vetapi.users.dtos.UserDto;
import mx.nopaloverflow.vetapi.users.exceptions.UserAlreadyRegisteredException;
import mx.nopaloverflow.vetapi.users.exceptions.InvalidCredentialsException;

public interface UserService {
    UserDto getUserForEmail(final String email);

    UserDto authenticateUser(final String username, final String password) throws InvalidCredentialsException;

    Long registerUser(final UserDto user) throws UserAlreadyRegisteredException;
}

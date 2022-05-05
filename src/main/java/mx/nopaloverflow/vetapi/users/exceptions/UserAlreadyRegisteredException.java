package mx.nopaloverflow.vetapi.users.exceptions;

import mx.nopaloverflow.vetapi.core.exceptions.BusinessException;

public class UserAlreadyRegisteredException extends BusinessException {

    public UserAlreadyRegisteredException(final String email) {
        super("User already registered with email " + email);
    }

}

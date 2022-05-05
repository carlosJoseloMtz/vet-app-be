package mx.nopaloverflow.vetapi.users.services;

import mx.nopaloverflow.vetapi.users.dtos.UserDto;

public interface SessionTokenService {
    String generateTokenForUser(final UserDto user);
}

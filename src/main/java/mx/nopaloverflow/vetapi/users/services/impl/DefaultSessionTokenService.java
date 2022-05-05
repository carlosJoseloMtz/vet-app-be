package mx.nopaloverflow.vetapi.users.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mx.nopaloverflow.vetapi.users.dtos.UserDto;
import mx.nopaloverflow.vetapi.users.services.SessionTokenService;

public class DefaultSessionTokenService implements SessionTokenService {

    private String issuerName = "vet-service";

    @Override
    public String generateTokenForUser(UserDto user) {
        final var algorithm = Algorithm.HMAC512(user.getEmail());
        return JWT.create()
                .withIssuer(issuerName)
                .sign(algorithm);
    }
}

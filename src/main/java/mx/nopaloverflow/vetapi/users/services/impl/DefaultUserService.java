package mx.nopaloverflow.vetapi.users.services.impl;

import mx.nopaloverflow.vetapi.core.encryption.EncryptionService;
import mx.nopaloverflow.vetapi.core.exceptions.EntityNotFoundException;
import mx.nopaloverflow.vetapi.users.dtos.UserDto;
import mx.nopaloverflow.vetapi.users.entities.UserEntity;
import mx.nopaloverflow.vetapi.users.exceptions.UserAlreadyRegisteredException;
import mx.nopaloverflow.vetapi.users.exceptions.InvalidCredentialsException;
import mx.nopaloverflow.vetapi.users.repositories.UserRepository;
import mx.nopaloverflow.vetapi.users.services.SessionTokenService;
import mx.nopaloverflow.vetapi.users.services.UserService;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class DefaultUserService implements UserService {

    final ModelMapper modelMapper;
    final UserRepository userRepository;
    final EncryptionService encryptionService;
    final SessionTokenService sessionTokenService;

    public DefaultUserService(final ModelMapper modelMapper,
                              final UserRepository userRepository,
                              final EncryptionService encryptionService,
                              final SessionTokenService sessionTokenService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    public UserDto getUserForEmail(final String email) {
        final var user = findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found [" + email + "]"));
        return userModel2Dto(user);
    }

    protected UserDto userModel2Dto(final UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    protected Optional<UserEntity> findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto authenticateUser(final String username, final String password) throws InvalidCredentialsException {
        final var foundUser = findUserByEmail(username);

        if (foundUser.isEmpty()) {
            throw new InvalidCredentialsException(String.format("User with email [%s] does not exist", username));
        }

        final var user = foundUser.get();

        final var isPasswordCorrect = encryptionService.isSameContent(password, user.getPassword());
        if (!isPasswordCorrect) {
            throw new InvalidCredentialsException(String.format("Credentials are invalid for user [%s]", username));
        }

        final var dto = userModel2Dto(user);

        final var sessionToken = sessionTokenService.generateTokenForUser(dto);
        dto.setSessionToken(sessionToken);

        return dto;
    }

    @Override
    public Long registerUser(final UserDto user) throws UserAlreadyRegisteredException {
        if (findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException("User already registered");
        }

        final var registration = modelMapper.map(user, UserEntity.class);
        final var pwd = encryptionService.encode(user.getRegistrationPassword());
        registration.setPassword(pwd);

        return userRepository.create(registration);
    }

    @Override
    public void updateUser(final Long id, final UserDto user) {
        final var pk = userRepository.findById(id)
                .map(UserEntity::getEntityPk)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User [%d] does not exist", id)));

        final var updatedEntity = modelMapper.map(user, UserEntity.class);
        updatedEntity.setEntityPk(pk);

        userRepository.update(updatedEntity);
    }
}

package mx.nopaloverflow.vetapi.users;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.encryption.EncryptionService;
import mx.nopaloverflow.vetapi.users.dtos.UserDto;
import mx.nopaloverflow.vetapi.users.entities.UserEntity;
import mx.nopaloverflow.vetapi.users.handlers.UsersHandler;
import mx.nopaloverflow.vetapi.users.repositories.UserRepository;
import mx.nopaloverflow.vetapi.users.repositories.impl.DefaultUserRepository;
import mx.nopaloverflow.vetapi.users.services.SessionTokenService;
import mx.nopaloverflow.vetapi.users.services.UserService;
import mx.nopaloverflow.vetapi.users.services.impl.DefaultSessionTokenService;
import mx.nopaloverflow.vetapi.users.services.impl.DefaultUserService;
import org.modelmapper.ModelMapper;

import static com.google.inject.name.Names.named;

public class UsersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SessionTokenService.class)
                .annotatedWith(named("sessionTokenService"))
                .to(DefaultSessionTokenService.class);
    }


    @Provides
    @Named("userRepository")
    UserRepository userRepository(@Named("connectionSource") final ConnectionSource connectionSource) {
        return new DefaultUserRepository(connectionSource);
    }

    @Provides
    @Named("usersHandler")
    UsersHandler usersHandler(@Named("userService") final UserService userService) {
        return new UsersHandler(userService);
    }


    @Provides
    @Named("usersModelMapper")
    ModelMapper usersModelMapper() {
        final var modelMapper = new ModelMapper();

        modelMapper
                .typeMap(UserEntity.class, UserDto.class)
                .addMapping(UserEntity::getEntityPk, UserDto::setId);

        modelMapper
                .typeMap(UserDto.class, UserEntity.class)
                .addMapping(UserDto::getPhone, UserEntity::setPhoneNumber);

        return modelMapper;
    }

    @Provides
    @Named("userService")
    UserService userService(
            @Named("usersModelMapper") final ModelMapper modelMapper,
            @Named("userRepository") final UserRepository userRepository,
            @Named("encryptionService") final EncryptionService encryptionService,
            @Named("sessionTokenService") final SessionTokenService sessionTokenService) {
        return new DefaultUserService(modelMapper, userRepository, encryptionService, sessionTokenService);
    }
}

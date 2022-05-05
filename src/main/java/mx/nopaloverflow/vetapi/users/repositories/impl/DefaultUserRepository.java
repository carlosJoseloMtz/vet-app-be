package mx.nopaloverflow.vetapi.users.repositories.impl;

import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.repositories.AbstractRepository;
import mx.nopaloverflow.vetapi.users.entities.UserEntity;
import mx.nopaloverflow.vetapi.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DefaultUserRepository extends AbstractRepository<UserEntity> implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserRepository.class);

    public DefaultUserRepository(final ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    public Optional<UserEntity> findByEmail(final String email) {
        final List<UserEntity> result;

        try {
            result = getEntityDao().queryForEq("email", email);
        } catch (SQLException e) {
            LOG.error("Error for SQL statement", e);
            return Optional.empty();
        }

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }
}

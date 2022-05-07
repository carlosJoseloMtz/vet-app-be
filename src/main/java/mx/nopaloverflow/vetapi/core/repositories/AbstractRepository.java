package mx.nopaloverflow.vetapi.core.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.entities.BaseEntity;
import mx.nopaloverflow.vetapi.core.exceptions.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractRepository<EntityType extends BaseEntity> implements VetCrudRepository<EntityType> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRepository.class);

    private final Dao<EntityType, Long> entityDao;
    private final ConnectionSource connectionSource;

    public AbstractRepository(final ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;

        Dao<EntityType, Long> entityDaoInstance;
        try {
            entityDaoInstance = DaoManager.createDao(connectionSource, getEntityClass());
        } catch (SQLException ex) {
            LOG.error("Error while trying to instantiate the entity dao", ex);
            entityDaoInstance = null;
        }

        this.entityDao = entityDaoInstance;
    }

    @Override
    public Long create(final EntityType entity) {
        try {
            final var performed = getEntityDao().create(entity);

            if (performed == 0) {
                throw new SystemException("Could not commit change");
            }

            return entity.getEntityPk();
        } catch (SQLException ex) {
            throw new SystemException("Unable to create entity", ex);
        }
    }

    @Override
    public Optional<EntityType> findById(final Long id) {
        EntityType result = null;
        try {
            result = getEntityDao().queryForId(id);
        } catch (SQLException ex) {
            throw new SystemException("Error while fetching item from the DB", ex);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public boolean isItemInDB(final Long id) {
        try {
            return getEntityDao().queryBuilder()
                    .where()
                    .eq("id", id)
                    .countOf() > 0;
        } catch (SQLException ex) {
            throw new SystemException(String.format("Error while querying entity with id [%d]", id), ex);
        }
    }

    @Override
    public void update(final EntityType entity) {
        try {
            getEntityDao().update(entity);
        } catch (SQLException ex) {
            throw new SystemException("Error while updating the entity", ex);
        }
    }

    protected abstract Class<EntityType> getEntityClass();

    public Dao<EntityType, Long> getEntityDao() {
        return entityDao;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}

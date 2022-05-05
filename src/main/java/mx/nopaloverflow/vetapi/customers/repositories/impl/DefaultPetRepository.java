package mx.nopaloverflow.vetapi.customers.repositories.impl;

import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.exceptions.SystemException;
import mx.nopaloverflow.vetapi.core.repositories.AbstractRepository;
import mx.nopaloverflow.vetapi.customers.entities.PetEntity;
import mx.nopaloverflow.vetapi.customers.repositories.PetRepository;

import java.sql.SQLException;
import java.util.List;

public class DefaultPetRepository extends AbstractRepository<PetEntity> implements PetRepository {

    public DefaultPetRepository(final ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    protected Class<PetEntity> getEntityClass() {
        return PetEntity.class;
    }

    @Override
    public List<PetEntity> findAllByOwner(final Long petOwnerId) {
        try {
            return getEntityDao().queryForEq("pet_owner", petOwnerId);
        } catch (SQLException ex) {
            throw new SystemException(
                    String.format("Error while trying to fetch all pets for owner [%d]", petOwnerId),
                    ex);
        }
    }
}

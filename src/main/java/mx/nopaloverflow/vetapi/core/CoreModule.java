package mx.nopaloverflow.vetapi.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import mx.nopaloverflow.vetapi.core.encryption.EncryptionService;
import mx.nopaloverflow.vetapi.core.encryption.impl.DefaultEncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static com.google.inject.name.Names.named;
import static java.util.Optional.ofNullable;

public class CoreModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(CoreModule.class);
    private static final String DEFAULT_HOSTNAME = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DB_NAME = "vet_db";
    private static final String DEFAULT_DB_USER = "root";
    private static final String DEFAULT_DB_PWD = "pass";

    @Override
    protected void configure() {
        bind(EncryptionService.class)
                .annotatedWith(named("encryptionService"))
                .to(DefaultEncryptionService.class);
    }

    private static String getVar(final String key, final String defaultValue) {
        return ofNullable(System.getenv(key))
                .orElse(defaultValue);
    }

    @Provides
    @Named("connectionSource")
    ConnectionSource connectionSource() throws SQLException {
        final var hostName = getVar("RDS_HOSTNAME", DEFAULT_HOSTNAME);
        final var port = getVar("RDS_PORT", DEFAULT_PORT);
        final var dbName = getVar("RDS_DB_NAME", DEFAULT_DB_NAME);
        final var username = getVar("RDS_USERNAME", DEFAULT_DB_USER);
        final var password = getVar("RDS_PASSWORD", DEFAULT_DB_PWD);

        final var url = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;

        LOG.debug("Using jdbc connection [{}]", url);

        final var dbType = new MysqlDatabaseType();
        return new JdbcPooledConnectionSource(url, username, password, dbType);
    }
}

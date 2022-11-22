package ru.zulvit.flyway;

import org.jetbrains.annotations.NotNull;
import org.flywaydb.core.Flyway;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class FlywayInitializer {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final Logger logger = Logger.getLogger(FlywayInitializer.class.getName());

    public static void initDb() {
        final Flyway flyway = Flyway.configure()
                .dataSource(
                        CREDS.url(),
                        CREDS.login(),
                        CREDS.password()
                )
                .cleanDisabled(false)
                .locations("db")
                .loggers()
                .load();
        flyway.clean();
        flyway.migrate();
        logger.log(Level.INFO, "Flyway migrate");
    }
}

package nl.tijsbeek.pictureprojectstool.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class DbHandler {

    private DbHandler() {}

    public static void enableDatabase(ConfigurableApplicationContext context) {
        try {
            runFlyway(context.getEnvironment());
            generateJooq(context.getEnvironment());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void runFlyway(ConfigurableEnvironment environment) {

        ClassicConfiguration conf = new ClassicConfiguration();
        String flywayUrl = environment.getProperty("spring.flyway.url");
        String flywayUser = environment.getProperty("spring.flyway.user");
        String flywayPassword = environment.getProperty("spring.flyway.password");

        conf.setDataSource(flywayUrl, flywayUser, flywayPassword);

        Flyway flyway = new Flyway(conf);
        flyway.migrate();
    }

    public static void generateJooq(ConfigurableEnvironment environment) throws Exception {
        Configuration configuration = new Configuration()

                // Configure the database connection here
                .withJdbc(new Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl(environment.getProperty("spring.flyway.url"))
                        .withUser(environment.getProperty("spring.flyway.user"))
                        .withPassword(environment.getProperty("spring.flyway.password"))
                )
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withExcludes("flyway_schema_history|_Pg.*|pg_.*|sql_.*")
                                .withSchemata(new SchemaMappingType().withInputSchema("public"))
                        )

                        // Generation flags: See advanced configuration properties
                        .withGenerate(new Generate()
                                .withRecords(true)
                                .withImmutablePojos(true)
                                .withFluentSetters(true))
                        .withTarget(new Target()
                                .withPackageName("nl.tijsbeek.pictureprojectstool.db.generated")
                                .withDirectory("/src/main/java")
                        )
                );


        GenerationTool.generate(configuration);
    }
}
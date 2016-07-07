package br.com.deroldo.form.config;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedMongoConfig {

    @SuppressWarnings("deprecation")
    private static final Version VERSION = Version.V3_0_6;
    private static final int PORT = 12345;

    @Bean
    Mongo mongo() throws Exception {
        return new EmbeddedMongoBuilder()
                .version(VERSION)
                .port(PORT)
                .build();
    }

}

package org.example.config.module;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.config.Configuration;

@Module
public class ConfigModule {
    private static final Logger LOGGER = Logger.getLogger(ConfigModule.class.getName());

    @Provides
    @Singleton
    public Configuration getConfiguration() {
        Properties props = readProperties();
        return new Configuration(props);
    }

    protected Properties readProperties(){
        InputStream is = this.getClass().getResourceAsStream("/app.properties");
        Properties props = new Properties();
        try {
            if (is != null) {
                props.load(is);
            } else {
                LOGGER.log(Level.SEVERE, "Could not find app.properties file");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while reading app.properties file", e);
        }
        return props;
    }
}

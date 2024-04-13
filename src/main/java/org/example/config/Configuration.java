package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private final int rateLimit;

    public Configuration() {
        Properties props = readProperties();
        if (props != null) {
            // default value will be 0 if property is not present
            rateLimit = Integer.parseInt(props.getProperty("firewall.ratelimit", "0"));
        } else {
            rateLimit = 0;
        }
    }

    public int getRateLimit() {
        return rateLimit;
    }

    /**
     * Loads properties defined in the app.properties file.
     * @return a Properties object containing the loaded properties
     */
    protected Properties readProperties(){
        InputStream is = this.getClass().getResourceAsStream("/app.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

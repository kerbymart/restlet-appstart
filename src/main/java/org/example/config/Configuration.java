package org.example.config;

import java.util.Properties;

public class Configuration {
    private final int rateLimit;

    public Configuration(Properties properties) {
        if (properties != null) {
            // default value will be 0 if property is not present
            rateLimit = Integer.parseInt(properties.getProperty("firewall.ratelimit", "0"));
        } else {
            rateLimit = 0;
        }
    }

    public int getRateLimit() {
        return rateLimit;
    }
}

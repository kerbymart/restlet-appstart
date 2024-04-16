package org.example.resources.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.Configuration;
import org.example.config.module.ConfigModule;
import org.example.resources.HelloWorldResource;
import org.example.resources.model.Greeting;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

public class HelloWorldServerResource extends org.restlet.resource.ServerResource
    implements HelloWorldResource {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldServerResource.class.getName());

    private String name;

    @Inject
    Configuration configuration;

    public HelloWorldServerResource() {
    }

    @Override
    protected void doInit() throws ResourceException {
        // Extract the "name" query parameter from the request
        this.name = getQueryValue("name");
        if (this.name == null) {
            this.name = "world";  // default to "world" if no name is provided
        }
    }

    @Override
    public String postHello(String json) {
        ObjectMapper mapper = new ObjectMapper();

        LOGGER.info("JSON: " + json);
        LOGGER.info("Rate Limit: " + configuration.getRateLimit());

        try {
            mapper.registerSubtypes(Greeting.class);
            // Deserialize the JSON payload into a Greeting object
            Greeting greeting = mapper.readValue(json, Greeting.class);
            this.name = greeting.getName();
            return "hello, " + this.name;
        } catch (Exception e) {
            // Handle the error (e.g., log it, return an error message)
            return "Error deserializing JSON";
        }
    }
}

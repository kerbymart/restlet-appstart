package org.example.resources.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.Configuration;
import org.example.resources.HelloWorldResource;
import org.example.resources.model.Greeting;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import javax.inject.Inject;
import java.io.IOException;

public class HelloWorldServerResource extends org.restlet.resource.ServerResource
    implements HelloWorldResource {
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
        try {
            mapper.registerSubtypes(Greeting.class);
            // Deserialize the JSON payload into a Greeting object
            Greeting greeting = mapper.readValue(json, Greeting.class);
            this.name = greeting.getName();
            System.out.println("Rate Limit: " + configuration.getRateLimit());
            return "hello, " + this.name;
        } catch (IOException e) {
            // Handle the error (e.g., log it, return an error message)
            setStatus(Status.SERVER_ERROR_INTERNAL, e.getMessage());
            return "Error deserializing JSON";
        }
    }
}

package org.example.resources.server;

import org.example.resources.HelloWorldResource;
import org.restlet.resource.ResourceException;

public class HelloWorldServerResource extends org.restlet.resource.ServerResource
    implements HelloWorldResource {
    private String name;

    @Override
    protected void doInit() throws ResourceException {
        // Extract the "name" query parameter from the request
        this.name = getQueryValue("name");
        if (this.name == null) {
            this.name = "world";  // default to "world" if no name is provided
        }
    }

    @Override
    public String represent() {
        return "hello, " + this.name;
    }
}

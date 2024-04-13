package org.example.resources.server;

import org.example.resources.RootResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

public class RootServerResource extends  org.restlet.resource.ServerResource
    implements RootResource {
    @Override
    protected void doInit() throws ResourceException {
        // Initialization code if needed
    }

    @Get
    @Override
    public String represent() {
        return "welcome to the root resource";
    }
}

package org.example.resources.module;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Context;
import org.restlet.resource.ServerResource;

public class InjectableResourceHandler<T extends ServerResource> extends Restlet {
    private final ResourceSupplier<T> resourceFactory;

    public InjectableResourceHandler(Context context, ResourceSupplier<T> resourceFactory) {
        super(context);
        this.resourceFactory = resourceFactory;
    }

    @Override
    public void handle(Request request, Response response) {
        T resource = resourceFactory.get(); // Create the resource instance
        resource.init(getContext(), request, response); // Initialize with context, request, and response
        resource.handle(); // Handle the request
    }
}

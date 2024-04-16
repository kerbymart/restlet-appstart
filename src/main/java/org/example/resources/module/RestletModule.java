package org.example.resources.module;

import dagger.Module;
import dagger.Provides;
import org.example.resources.server.HelloWorldServerResource;
import org.example.resources.server.RootServerResource;
import org.restlet.Context;

import javax.inject.Singleton;

@Module
public class RestletModule {

    private final Context context;

    public RestletModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    HelloWorldServerResource provideHelloWorldServerResource() {
        return new HelloWorldServerResource();
    }

    @Provides
    @Singleton
    RootServerResource provideRootServerResource() {
        return new RootServerResource();
    }
}

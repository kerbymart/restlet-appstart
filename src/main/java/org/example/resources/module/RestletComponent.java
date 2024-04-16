package org.example.resources.module;

import dagger.Component;
import org.example.config.Configuration;
import org.example.config.module.ConfigModule;
import org.example.module.MainModule;
import org.example.resources.server.HelloWorldServerResource;
import org.example.resources.server.RootServerResource;
import org.restlet.Application;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MainModule.class, RestletModule.class, ConfigModule.class})
public interface RestletComponent {
    void inject(Application application);
    void inject(RootServerResource rootServerResource);
    void inject(HelloWorldServerResource helloWorldServerResource);
    void inject(Configuration configuration);
}


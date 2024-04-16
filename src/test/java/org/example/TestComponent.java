package org.example;

import dagger.Component;
import org.example.config.module.ConfigModule;
import org.example.module.MainModule;
import org.example.resources.module.RestletModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MainModule.class, RestletModule.class, ConfigModule.class})
public interface TestComponent {
    void inject(HelloEndpointVulnerabilityTest test);
}

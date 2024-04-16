package org.example.module;

import dagger.Module;
import dagger.Provides;
import org.restlet.Application;

import javax.inject.Singleton;

@Module
public class MainModule {

    private final Application application;

    public MainModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
package org.example.resources.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.example.resources.HelloWorldResource;
import org.example.resources.RootResource;
import org.example.resources.server.HelloWorldServerResource;
import org.example.resources.server.RootServerResource;
import org.restlet.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestConfigModule extends AbstractModule {

    private static final Logger LOG = Logger.getLogger(RestConfigModule.class.getName());
    private Context context;

    public RestConfigModule(){}

    /**
     * Constructor that accepts a Context object
     * @param context the Context object
     */
    public RestConfigModule(Context context) {
        super();
        this.context = context;
    }

    /**
     * Configures the bindings between interfaces and their implementation classes using Guice.
     */
    @Override
    protected void configure() {
        // Suppress Guice warning when on GAE
        // see https://code.google.com/p/google-guice/issues/detail?id=488
        Logger.getLogger("com.google.inject.internal.util").setLevel(Level.WARNING);
        bind(RootResource.class).to(RootServerResource.class).in(Scopes.SINGLETON);
        bind(HelloWorldResource.class).to(HelloWorldServerResource.class).in(Scopes.SINGLETON);
        Names.bindProperties(binder(), readProperties());
    }

    /**
     * Loads properties defined in the app.properties file.
     * @return a Properties object containing the loaded properties
     */
    protected Properties readProperties(){
        InputStream is = this.getClass().getResourceAsStream("/app.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

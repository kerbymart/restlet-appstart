package org.example;

import org.example.config.Configuration;
import org.example.docs.EnhancedSwagger2SpecificationRestlet;
import org.example.config.module.ConfigModule;
import org.example.module.MainModule;
import org.example.resources.module.InjectableResourceHandler;
import org.example.resources.module.*;
import org.example.resources.server.HelloWorldServerResource;
import org.example.resources.server.RootServerResource;
import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.engine.application.CorsFilter;
import org.restlet.ext.apispark.internal.firewall.FirewallFilter;
import org.restlet.ext.apispark.internal.firewall.handler.RateLimitationHandler;
import org.restlet.ext.apispark.internal.firewall.handler.policy.UniqueLimitPolicy;
import org.restlet.ext.apispark.internal.firewall.rule.FirewallRule;
import org.restlet.ext.apispark.internal.firewall.rule.PeriodicFirewallCounterRule;
import org.restlet.ext.apispark.internal.firewall.rule.policy.IpAddressCountingPolicy;
import org.restlet.ext.swagger.Swagger2SpecificationRestlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private RestletComponent restletComponent;

    @Inject
    Configuration configuration;

    public static void main(String[] args) throws Exception {
        // Set up and start the main application component, binding it to an HTTP server on port 8080.
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
        component.getDefaultHost().attach("", new Main());
        component.start();
    }

    @Override
    public Restlet createInboundRoot() {

        ConfigModule configModule = new ConfigModule();
        configuration = configModule.getConfiguration();

        restletComponent = DaggerRestletComponent.builder()
                .mainModule(new MainModule(this))
                .restletModule(new RestletModule(this.getContext()))
                .configModule(configModule)
                .build();

        restletComponent.inject(this);

        // Set up the router to map incoming requests to their respective resources.
        Router router = new Router(getContext());

        HelloWorldServerResource helloWorldServerResource = new HelloWorldServerResource();
        RootServerResource rootServerResource = new RootServerResource();

        restletComponent.inject(rootServerResource);
        restletComponent.inject(helloWorldServerResource);

        router.attachDefault(new InjectableResourceHandler<>(getContext(), () -> rootServerResource));
        router.attach("/hello", new InjectableResourceHandler<>(getContext(), () -> helloWorldServerResource));

        // Provide UI for API visualization and testing.
        attachSwaggerUI(router);
        attachSwaggerSpecification2(router);

        // Enable CORS to allow secure cross-origin requests.
        CorsFilter corsFilter = createCORSFilter(router);

        // Introduce rate limiting to protect the API from excessive requests.
        int rateLimit = configuration.getRateLimit();
        FirewallFilter firewallFilter = createFirewallFilter(corsFilter, rateLimit);

        return firewallFilter;
    }

    private FirewallFilter createFirewallFilter(Restlet next, int rateLimit) {
        // Implement a firewall rule for IP-based rate limiting. This helps prevent single-source request flooding.
        FirewallRule rule = new PeriodicFirewallCounterRule(60, TimeUnit.SECONDS, new IpAddressCountingPolicy());
        ((PeriodicFirewallCounterRule)rule).addHandler(new RateLimitationHandler(new UniqueLimitPolicy(rateLimit)));
        FirewallFilter firewallFiler = new FirewallFilter(getContext(), Collections.singletonList(rule));
        firewallFiler.setNext(next);

        return firewallFiler;
    }

    private CorsFilter createCORSFilter(Router router) {
        // Set up CORS support to enable secure cross-origin requests, crucial for modern web apps.
        CorsFilter corsFilter = new CorsFilter(getContext());
        corsFilter.setAllowedOrigins(new HashSet(Collections.singletonList("*")));
        corsFilter.setAllowedCredentials(true);

        corsFilter.setNext(router);
        return corsFilter;
    }

    private void attachSwaggerUI(Router router) {
        // Integrate SwaggerUI to offer an interactive API documentation interface.
        Directory swaggerUiDirectory = new Directory(getContext(), "war:///swagger-ui/");
        router.attach("/ui/", swaggerUiDirectory);
    }

    private void attachSwaggerSpecification2(Router router) {
        // Attach the Swagger V2 specification, giving a standardized view of our API's capabilities.
        Swagger2SpecificationRestlet swagger2SpecificationRestlet
                = new EnhancedSwagger2SpecificationRestlet(this);
        swagger2SpecificationRestlet.setBasePath("http://localhost:8080/");
        swagger2SpecificationRestlet.attach(router, "/api-docs.json");
    }
}
package org.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
@RunAsClient
public class MainTest {

    final static Logger LOG = Logger.getLogger(MainTest.class.getName());

    private Client client;

    @ArquillianResource
    URL deploymentUrl;

    @Before
    public void setUp() {
        client = new Client(Protocol.HTTP);
    }

    @After
    public void tearDown() throws Exception {
        if (client != null) {
            client.stop();
        }
    }

    @Deployment(name = "default")
    public static WebArchive getTestArchive() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        File apppropfile = new File("src/main/resources/app.properties");

        return ShrinkWrap.create(WebArchive.class, "simple.war")
                .addPackages(true, "org.example")
                .addAsResource(apppropfile, "app.properties")
                .addAsLibraries(files)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/appengine-web.xml"), "appengine-web.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/logging.properties"), "logging.properties");
    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        String targetUrl = deploymentUrl.toString() +"/hello";

        LOG.info("Target URL: " + targetUrl);

        Request request = new Request(Method.GET, targetUrl);
        Response response = client.handle(request);

        assertNotNull(response);
        assertEquals(Status.SUCCESS_OK, response.getStatus());
        assertEquals("Verify that the servlet was deployed and returns expected result",
                "hello, world", response.getEntityAsText());
    }
}

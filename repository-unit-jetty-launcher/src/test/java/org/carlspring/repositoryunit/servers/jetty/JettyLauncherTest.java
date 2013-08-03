package org.carlspring.repositoryunit.servers.jetty;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.carlspring.strongbox.annotations.ArtifactExistenceState;
import org.carlspring.strongbox.annotations.ArtifactResource;
import org.carlspring.strongbox.annotations.ArtifactResourceMapper;
import org.carlspring.strongbox.annotations.RequiresArtifactResource;
import org.carlspring.repositoryunit.testing.AbstractRepositoryUnitTestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertTrue;

/**
 * @author mtodorov
 */
@RequiresArtifactResource( artifactResources = { @ArtifactResource( repository = "snapshots",
                                                                    groupId = "org.carlspring.maven",
                                                                    artifactId = "my-maven-plugin",
                                                                    version = "1.0-SNAPSHOT",
                                                                    length = 10000,
                                                                    state = ArtifactExistenceState.EXISTS) })
public class JettyLauncherTest extends AbstractRepositoryUnitTestCase
{

    public static final int PORT = 48080;

    public static final String ARTIFACT_URL = "http://localhost:" + PORT + "/nexus/content/repositories/snapshots/" +
                                              "org/carlspring/maven/" +
                                              "my-maven-plugin/" +
                                              "1.0-SNAPSHOT/" +
                                              "my-maven-plugin-1.0-SNAPSHOT.jar";


    @Override
    @Before
    public void setUp()
            throws Exception
    {
        super.setUp();
    }

    @Override
    protected Class getRepresentationClass()
    {
        return getClass();
    }

    @Test
    public void testLaunchWar()
            throws Exception
    {
        final JettyLauncher launcher = new JettyLauncher();

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    launcher.setBasedir(new File("target/web/war").getCanonicalPath());
                    launcher.setWar("target/web/war/strongbox-webapp.war");
                    launcher.startWarInstance();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        while (launcher.getServer() == null || launcher.getServer().isStarting())
        {
            Thread.sleep(250l);
        }

        Client client = Client.create();

        System.out.println("Getting " + ARTIFACT_URL);

        final ArtifactResource resource = ArtifactResourceMapper.getResource("org.carlspring.maven",
                                                                             "my-maven-plugin",
                                                                             "1.0-SNAPSHOT");

        WebResource webResource = client.resource(ARTIFACT_URL);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);

        try
        {
            assertTrue("Failed to resolve artifact!", response.getStatus() == 200);
        }
        finally
        {
            launcher.getServer().stop();
        }
    }

    @Test
    public void testLaunchExploded()
            throws Exception
    {
        final JettyLauncher launcher = new JettyLauncher();

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    launcher.setBasedir(new File("target/web/exploded").getCanonicalPath());
                    launcher.startExplodedInstance();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        while (launcher.getServer() == null || launcher.getServer().isStarting())
        {
            Thread.sleep(250l);
        }

        Client client = Client.create();

        System.out.println("Getting " + ARTIFACT_URL);

        WebResource webResource = client.resource(ARTIFACT_URL);
        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);

        try
        {
            assertTrue("Failed to resolve artifact!", response.getStatus() == 200);
        }
        finally
        {
            launcher.getServer().stop();
        }
    }

}

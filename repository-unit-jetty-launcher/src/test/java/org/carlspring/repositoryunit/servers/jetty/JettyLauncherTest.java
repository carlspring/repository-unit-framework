package org.carlspring.repositoryunit.servers.jetty;

import org.carlspring.maven.commons.util.ArtifactUtils;
import org.carlspring.repositoryunit.testing.AbstractRepositoryUnitTestCase;
import org.carlspring.strongbox.client.ArtifactClient;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mtodorov
 */
public class JettyLauncherTest
        extends AbstractRepositoryUnitTestCase
{

    public static final int PORT = 48080;

    public static final String ARTIFACT_URL = "http://localhost:" + PORT + "/storages/storage0/snapshots/" +
                                              "org/carlspring/maven/" +
                                              "my-maven-plugin/" +
                                              "1.0-SNAPSHOT/" +
                                              "my-maven-plugin-1.0-SNAPSHOT.jar";


    @Override
    @Before
    public void setUp()
            throws Exception
    {
        // Overriding method in order to skip injection.
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

        System.out.println("Add artifact...");

        ArtifactClient client = new ArtifactClient();
        client.setContextBaseUrl("storages/storage0");
        client.addArtifact(ArtifactUtils.getArtifactFromGAV("org.carlspring.maven:my-maven-plugin:1.0-SNAPSHOT"),
                           "snapshots",
                           10000L);

        System.out.println("Getting " + ARTIFACT_URL);
        client.getArtifact(ArtifactUtils.getArtifactFromGAV("org.carlspring.maven:my-maven-plugin:1.0-SNAPSHOT"),
                           "snapshots");

        launcher.stopServer();
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

        System.out.println("Add artifact...");

        ArtifactClient client = new ArtifactClient();
        client.setContextBaseUrl("storages/storage0");
        client.addArtifact(ArtifactUtils.getArtifactFromGAV("org.carlspring.maven:my-maven-plugin:1.0-SNAPSHOT"),
                           "snapshots",
                           10000L);

        System.out.println("Getting " + ARTIFACT_URL);
        client.getArtifact(ArtifactUtils.getArtifactFromGAV("org.carlspring.maven:my-maven-plugin:1.0-SNAPSHOT"),
                           "snapshots");

        launcher.stopServer();
    }

}

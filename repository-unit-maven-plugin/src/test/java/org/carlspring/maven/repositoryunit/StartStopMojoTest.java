package org.carlspring.maven.repositoryunit;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.settings.Settings;

import java.io.File;

/**
 * @author mtodorov
 */
public class StartStopMojoTest
        extends AbstractMojoTestCase
{

    private static final String POM_PLUGIN = "target/test-classes/poms/start-stop-pom.xml";

    StartRepositoryMojo startMojo;

    StopRepositoryMojo stopMojo;


    @Override
    public void setUp()
            throws Exception
    {
        super.setUp();

        // This is because Maven's testing harness is so half-assed and doesn't mock enough for you.
        // If your settings.xml isn't in the default location, sorry. If anyone has any better ideas,
        // let me know. :)
        Settings settings = new Settings();
        settings.setLocalRepository(System.getProperty("user.home") + "/.m2/repository");

        startMojo = (StartRepositoryMojo) lookupMojo("start", POM_PLUGIN);
        startMojo.setBasedir(new File(".").getCanonicalPath());
        startMojo.setSettings(settings);
        startMojo.setTimeout(15);

        stopMojo = (StopRepositoryMojo) lookupMojo("stop", POM_PLUGIN);
        stopMojo.setBasedir(new File(".").getCanonicalPath());
        stopMojo.setSettings(settings);
        stopMojo.setTimeout(15);
    }

    public void testStartStopTest()
            throws MojoFailureException, MojoExecutionException
    {
        startMojo.execute();

        // Check the repository is ready for connections.
        assertTrue("Failed to start the repository!", startMojo.getJettyLauncher().getServer().isStarted());

        stopMojo.execute();

        // Check the port is no longer accepting connections.
        assertTrue("Failed to stop the repository!", stopMojo.getJettyLauncher().getServer().isStopped());
    }

}

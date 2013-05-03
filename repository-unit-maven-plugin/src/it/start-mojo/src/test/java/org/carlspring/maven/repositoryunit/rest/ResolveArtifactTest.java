package org.carlspring.maven.repositoryunit.rest;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.carlspring.repositoryunit.client.ArtifactClient;
import org.carlspring.repositoryunit.testing.AbstractRepositoryUnitTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mtodorov
 */
public class ResolveArtifactTest
        extends AbstractRepositoryUnitTestCase
{

    public static final Artifact ARTIFACT = new DefaultArtifact("org.carlspring.maven",
                                                                "my-maven-plugin",
                                                                "1.0-SNAPSHOT",
                                                                "compile",
                                                                "jar",
                                                                null,
                                                                new DefaultArtifactHandler("jar"));


    public static final String REPOSITORY = "snapshots";


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
    public void testArtifactResolution()
            throws Exception
    {
        ArtifactClient client = new ArtifactClient();
        client.setContextBaseUrl("nexus/content/repositories");
        client.addArtifact(ARTIFACT, REPOSITORY, 10000l);

        // Get the artifact
        client.getArtifact(ARTIFACT, REPOSITORY);

        // Delete the artifact
        client.deleteArtifact(ARTIFACT, REPOSITORY);
    }

}

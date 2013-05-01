package org.carlspring.repositoryunit.testing;

import org.carlspring.repositoryunit.annotations.ArtifactExistenceState;
import org.carlspring.repositoryunit.annotations.ArtifactResource;
import org.carlspring.repositoryunit.annotations.ArtifactResourceMapper;
import org.carlspring.repositoryunit.annotations.RequiresArtifactResource;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author mtodorov
 */
@RequiresArtifactResource ( artifactResources = { @ArtifactResource( repository = "releases",
                                                                     groupId = "org.carlspring",
                                                                     artifactId = "test",
                                                                     version = "1.2.3",
                                                                     length = 1000000,
                                                                     state = ArtifactExistenceState.EXISTS) })
public class RepositoryUnitTest extends AbstractRepositoryUnitTestCase
{

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
    public void testInjection()
    {
        final ArtifactResource resource = ArtifactResourceMapper.getResource("org.carlspring", "test", "1.2.3");

        assertNotNull("Failed to map artifact resource!", resource);
        assertEquals("Failed to map artifact resource!", "releases", resource.repository());
        assertEquals("Failed to map artifact resource!", "org.carlspring", resource.groupId());
        assertEquals("Failed to map artifact resource!", "test", resource.artifactId());
        assertEquals("Failed to map artifact resource!", "1.2.3", resource.version());
        assertEquals("Failed to map artifact resource!", "jar", resource.type());
        assertEquals("Failed to map artifact resource!", 1000000, resource.length());
        assertEquals("Failed to map artifact resource!", ArtifactExistenceState.EXISTS, resource.state());
    }

}
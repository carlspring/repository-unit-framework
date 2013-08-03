package org.carlspring.repositoryunit.testing;

import junit.framework.Assert;
import org.carlspring.strongbox.annotations.ArtifactExistenceState;
import org.carlspring.strongbox.annotations.ArtifactResource;
import org.carlspring.strongbox.annotations.ArtifactResourceMapper;
import org.carlspring.strongbox.annotations.RequiresArtifactResource;
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
        Assert.assertEquals("Failed to map artifact resource!", "releases", resource.repository());
        Assert.assertEquals("Failed to map artifact resource!", "org.carlspring", resource.groupId());
        Assert.assertEquals("Failed to map artifact resource!", "test", resource.artifactId());
        Assert.assertEquals("Failed to map artifact resource!", "1.2.3", resource.version());
        Assert.assertEquals("Failed to map artifact resource!", "jar", resource.type());
        Assert.assertEquals("Failed to map artifact resource!", 1000000, resource.length());
        Assert.assertEquals("Failed to map artifact resource!", ArtifactExistenceState.EXISTS, resource.state());
    }

}

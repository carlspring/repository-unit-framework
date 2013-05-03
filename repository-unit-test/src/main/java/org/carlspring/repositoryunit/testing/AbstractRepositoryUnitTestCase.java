package org.carlspring.repositoryunit.testing;

import org.carlspring.repositoryunit.annotations.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mtodorov
 */
public abstract class AbstractRepositoryUnitTestCase
{


    protected void setUp()
            throws Exception
    {
        inject();
    }

    /* IoC part start.*/

    private void inject()
            throws InjectionException
    {
        getAllAnnotations(new ArrayList<Annotation>(), getRepresentationClass());
    }

    protected abstract Class getRepresentationClass();

    public List<Annotation> getAllAnnotations(List<Annotation> annotations,
                                              Class<?> clazz)
    {
        Collections.addAll(annotations, clazz.getAnnotations());

        for (Annotation annotation : annotations)
        {
            System.out.println("class: " + clazz.getCanonicalName());
            System.out.println("  annotation: " + annotation.toString());

            if (annotation instanceof RequiresArtifactResource)
            {
                RequiresArtifactResource requiresArtifactResource = (RequiresArtifactResource) annotation;
                for (ArtifactResource resource : requiresArtifactResource.artifactResources())
                {
                    ArtifactResourceMapper.addResource(resource);
                }
            }
        }

        return annotations;
    }

    /* IoC part end. */

    /* Mock methods start. */
    public void addArtifactResource(String repository,
                                    String groupId,
                                    String artifactId,
                                    String version,
                                    long length)
    {
        addArtifactResource(repository,
                            groupId,
                            artifactId,
                            "jar",
                            version,
                            "",
                            length,
                            ArtifactExistenceState.EXISTS);
    }

    public void addArtifactResource(String repository,
                                    String groupId,
                                    String artifactId,
                                    String version,
                                    String type,
                                    long length)
    {
        addArtifactResource(repository,
                            groupId,
                            artifactId,
                            type,
                            version,
                            "",
                            length,
                            ArtifactExistenceState.EXISTS);
    }

    public void addArtifactResource(String repository,
                                    String groupId,
                                    String artifactId,
                                    String version,
                                    String type,
                                    String classifier,
                                    long length,
                                    ArtifactExistenceState state)
    {
        ArtifactResource resource = ArtifactResourceMapper.getArtifactResourceInstance(repository,
                                                                                       groupId,
                                                                                       artifactId,
                                                                                       type,
                                                                                       version,
                                                                                       classifier,
                                                                                       length,
                                                                                       state);
        ArtifactResourceMapper.addResource(resource);
    }

    /* Mock methods end. */

}

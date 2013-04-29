package org.carlspring.repositoryunit.testing;

import org.carlspring.repositoryunit.annotations.ArtifactResource;
import org.carlspring.repositoryunit.annotations.ArtifactResourceMapper;
import org.carlspring.repositoryunit.annotations.InjectionException;
import org.carlspring.repositoryunit.annotations.RequiresArtifactResource;

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

}

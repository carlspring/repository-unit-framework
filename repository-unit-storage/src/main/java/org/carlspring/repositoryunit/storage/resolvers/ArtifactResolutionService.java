package org.carlspring.repositoryunit.storage.resolvers;

import org.apache.maven.artifact.Artifact;
import org.carlspring.repositoryunit.storage.DataCenter;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class ArtifactResolutionService
{

    private boolean inMemoryModeOnly = true; //false;

    private boolean allowInMemory = false;

    private Set<LocationResolver> resolvers = new LinkedHashSet<LocationResolver>();

    private static ArtifactResolutionService instance = new ArtifactResolutionService();

    private boolean initialized = false;


    public static ArtifactResolutionService getInstance()
    {
        return instance;
    }

    public void initialize()
    {
        if (initialized)
        {
            return;
        }

        if (inMemoryModeOnly)
        {
            InMemoryLocationResolver resolver = new InMemoryLocationResolver();
            resolvers.add(resolver);
        }
        else
        {
            FSLocationResolver fsResolver = new FSLocationResolver();
            InMemoryLocationResolver inMemoryResolver = new InMemoryLocationResolver();

            resolvers.add(fsResolver);

            if (allowInMemory)
            {
                resolvers.add(inMemoryResolver);
            }
        }

        initialized = true;
    }

    public InputStream getInputStreamForArtifact(String repository, Artifact artifact)
            throws ArtifactResolutionException
    {
        InputStream is = null;

        for (LocationResolver resolver : resolvers)
        {
            is = resolver.getInputStreamForArtifact(repository, artifact);
            if (is != null)
            {
                break;
            }
        }

        if (is == null)
        {
            throw new ArtifactResolutionException("Artifact " + artifact.toString() + " not found.");
        }

        return is;
    }

    public boolean isInMemoryModeOnly()
    {
        return inMemoryModeOnly;
    }

    public void setInMemoryModeOnly(boolean inMemoryModeOnly)
    {
        this.inMemoryModeOnly = inMemoryModeOnly;
    }

    public boolean allowsInMemory()
    {
        return allowInMemory;
    }

    public void setAllowInMemory(boolean allowInMemory)
    {
        this.allowInMemory = allowInMemory;
    }

    public Set<LocationResolver> getResolvers()
    {
        return resolvers;
    }

    public void setResolvers(Set<LocationResolver> resolvers)
    {
        this.resolvers = resolvers;
    }

}

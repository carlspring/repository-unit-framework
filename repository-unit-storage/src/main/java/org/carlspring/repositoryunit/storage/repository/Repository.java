package org.carlspring.repositoryunit.storage.repository;

import org.apache.maven.artifact.Artifact;
import org.carlspring.maven.commons.util.ArtifactUtils;

import java.io.File;

/**
 * @author mtodorov
 */
public class Repository
{

    private String name;

    private String basedir;

    private int policy = RepositoryPolicyEnum.MIXED.getPolicy();

    private int layout = RepositoryLayoutEnum.MAVEN_2.getLayout();

    private int type = RepositoryTypeEnum.HOSTED.getType();


    public Repository()
    {
    }

    public boolean containsArtifact(Artifact artifact)
    {
        final String artifactPath = ArtifactUtils.convertArtifactToPath(artifact);
        final File artifactFile = new File(basedir, artifactPath);

        return artifactFile.exists();
    }

    public boolean allowsSnapshots()
    {
        return policy == RepositoryPolicyEnum.SNAPSHOT.getPolicy() ||
               policy == RepositoryPolicyEnum.MIXED.getPolicy();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBasedir()
    {
        return basedir;
    }

    public void setBasedir(String basedir)
    {
        this.basedir = basedir;
    }

    public int getPolicy()
    {
        return policy;
    }

    public void setPolicy(int policy)
    {
        this.policy = policy;
    }

    public int getLayout()
    {
        return layout;
    }

    public void setLayout(int layout)
    {
        this.layout = layout;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

}

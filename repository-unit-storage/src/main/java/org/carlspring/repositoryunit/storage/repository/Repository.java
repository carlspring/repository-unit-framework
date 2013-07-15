package org.carlspring.repositoryunit.storage.repository;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.apache.maven.artifact.Artifact;
import org.carlspring.maven.commons.util.ArtifactUtils;

import java.io.File;

/**
 * @author mtodorov
 */
public class Repository
{

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String policy = RepositoryPolicyEnum.MIXED.getPolicy();

    @XStreamAsAttribute
    private String layout = RepositoryLayoutEnum.MAVEN_2.getLayout();

    @XStreamAsAttribute
    private String type = RepositoryTypeEnum.HOSTED.getType();


    public Repository()
    {
    }

    public Repository(String name)
    {
        this.name = name;
    }

    public boolean containsArtifact(Artifact artifact)
    {
        final String artifactPath = ArtifactUtils.convertArtifactToPath(artifact);
        final File artifactFile = new File(name, artifactPath);

        return artifactFile.exists();
    }

    public boolean allowsSnapshots()
    {
        return policy.equals(RepositoryPolicyEnum.SNAPSHOT.getPolicy()) ||
               policy.equals(RepositoryPolicyEnum.MIXED.getPolicy());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPolicy()
    {
        return policy;
    }

    public void setPolicy(String policy)
    {
        this.policy = policy;
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return name;
    }

}

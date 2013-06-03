package org.carlspring.repositoryunit.storage;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class Storage
{

    private String basedir;

    private Set<String> repositories = new LinkedHashSet<String>();


    public Storage()
    {
    }

    public Storage(String basedir)
    {
        this.basedir = basedir;
    }

    public String getBasedir()
    {
        return basedir;
    }

    public void setBasedir(String basedir)
    {
        this.basedir = basedir;
    }

    public Set<String> getRepositories()
    {
        return repositories;
    }

    public void setRepositories(Set<String> repositories)
    {
        this.repositories = repositories;
    }

    public void addRepository(String repository)
    {
        repositories.add(repository);
    }

    public void removeRepository(String repository)
    {
        repositories.remove(repository);
    }

    public boolean hasRepositories()
    {
        return repositories.size() > 0;
    }

}

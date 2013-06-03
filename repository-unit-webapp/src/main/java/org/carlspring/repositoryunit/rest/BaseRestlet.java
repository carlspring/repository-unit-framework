package org.carlspring.repositoryunit.rest;

import org.carlspring.repositoryunit.storage.RepositoryLocator;
import org.carlspring.repositoryunit.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.io.IOException;

/**
 * @author Martin Todorov
 */
public abstract class BaseRestlet
{

    private static final Logger logger = LoggerFactory.getLogger(BaseRestlet.class);

    private boolean initialized;

    private Storage storage = new Storage();


    public void initialize()
            throws IOException, NamingException
    {
        initialized = true;

        initializeStorageConfiguration();
    }

    private void initializeStorageConfiguration()
            throws IOException
    {
        RepositoryLocator repositoryLocator = new RepositoryLocator(storage);
        repositoryLocator.initializeRepositories();
    }

    public boolean isInitialized()
    {
        return initialized;
    }

    public void setInitialized(boolean initialized)
    {
        this.initialized = initialized;
    }

}

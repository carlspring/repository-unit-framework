package org.carlspring.repositoryunit.rest;

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


    public void initialize()
            throws IOException, NamingException
    {
        initialized = true;
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

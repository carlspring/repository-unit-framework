package org.carlspring.repositoryunit.storage;

import org.carlspring.maven.commons.io.filters.DirectoryFilter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author mtodorov
 */
public class RepositoryLocator
{

    private Storage storage;


    public RepositoryLocator(Storage storage)
    {
        this.storage = storage;
    }

    public void initializeRepositories()
            throws IOException
    {
        if (storage.getBasedir() != null)
        {
            File basedir = new File(storage.getBasedir()).getCanonicalFile();
            final DirectoryFilter filter = getDirectoryFilter();

            File[] directories = basedir.listFiles(filter);
            if (directories != null)
            {
                Arrays.sort(directories);

                for (File directory : directories)
                {
                    storage.addRepository(directory.getName());
                }
            }
        }
    }

    private DirectoryFilter getDirectoryFilter()
    {
        final DirectoryFilter filter = new DirectoryFilter();
        filter.addExclude(".*");
        filter.addExclude("*.");

        return filter;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }

}

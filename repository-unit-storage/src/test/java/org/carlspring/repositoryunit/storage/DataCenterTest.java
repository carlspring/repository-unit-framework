package org.carlspring.repositoryunit.storage;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author mtodorov
 */
public class DataCenterTest
{

    public static final String TEST_CLASSES_DIR = "target/test-classes";
    public static final String TEST_REPOSITORIES_DIR = TEST_CLASSES_DIR + "/repositories";
    public static final String TEST_STORAGES_DIR = TEST_CLASSES_DIR + "/storages";


    @Test
    public void testAddAnonymous()
    {
        Storage storage1 = new Storage();
        storage1.setBasedir("/foo/bar/anonymous");

        Storage storage2 = new Storage();
        storage2.setBasedir("/foo/bar/storage");

        DataCenter dataCenter = new DataCenter();
        dataCenter.addStorage(null, storage1);
        dataCenter.addStorage(storage2.getBasedir(), storage2);

        assertEquals("Incorrect number of storages!", dataCenter.getStorages().size(), 2);
        assertNotNull("Failed to add named storage!", dataCenter.getStorages().get("/foo/bar/storage"));
        assertNotNull("Failed to add anonymous storage!", dataCenter.getStorages().get("anonymous-storage-1"));
    }

    @Test
    public void testAddRepositoriesAsAnonymousStorages()
            throws IOException
    {
        Set<String> repositories = new LinkedHashSet<String>();
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository0").getCanonicalPath());
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository1").getCanonicalPath());
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository2").getCanonicalPath());

        DataCenter dataCenter = new DataCenter();
        dataCenter.addRepositories(repositories);

        assertEquals("Failed to add anonymous storages!", 3, dataCenter.getStorages().keySet().size());
    }

    @Test
    public void testAddStorageAndLocateRepositories()
            throws IOException
    {
        Storage storage0 = new Storage(new File(TEST_STORAGES_DIR, "storage0").getCanonicalPath());
        Storage storage1 = new Storage(new File(TEST_STORAGES_DIR, "storage1").getCanonicalPath());
        Storage storage2 = new Storage(new File(TEST_STORAGES_DIR, "storage2").getCanonicalPath());

        Collection<Storage> storages = new ArrayList<Storage>();
        storages.add(storage0);
        storages.add(storage1);
        storages.add(storage2);

        DataCenter dataCenter = new DataCenter();
        dataCenter.addStorages(storages);

        dataCenter.initializeStorages();

        Set<String> repositories = new LinkedHashSet<String>();
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository0").getCanonicalPath());
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository1").getCanonicalPath());
        repositories.add(new File(TEST_REPOSITORIES_DIR, "repository2").getCanonicalPath());

        dataCenter.addRepositories(repositories);

        assertEquals("Incorrect number of storages!", 6, dataCenter.getStorages().size());

        for (Map.Entry entry : dataCenter.getStorages().entrySet())
        {
            Storage storage = (Storage) entry.getValue();

            System.out.println(entry.getKey() +": " + storage.getBasedir());
            for (String repository : storage.getRepositories())
            {
                System.out.println(" --> " + repository);
            }
        }

    }

}

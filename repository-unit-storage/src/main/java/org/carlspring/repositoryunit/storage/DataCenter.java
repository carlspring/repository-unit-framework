package org.carlspring.repositoryunit.storage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

 /**
 * @author mtodorov
 */
public class DataCenter
{

    public static final String STORAGE_PREFIX = "anonymous-storage";

    private AtomicLong anonymousStoragesCounter = new AtomicLong();

    /**
     * A map of storages.
     *
     * Normally, there would be just one storage containing multiple repositories.
     * However, sometimes this may not always the case. For example, if one would
     * like to use both a backed up storage in parallel with the original one,
     * this feature would help.
     */
    private Map<String, Storage> storages = new LinkedHashMap<String, Storage>();


    public DataCenter()
    {
    }

    public void initializeStorages()
            throws IOException
    {
        for (Map.Entry entry : storages.entrySet())
        {
            final Storage storage = (Storage) entry.getValue();

            RepositoryLocator locator = new RepositoryLocator(storage);
            locator.initializeRepositories();
        }
    }

    /**
     * Add repositories which are not associated with a particular storage,
     *
     * @param repositories The absolute paths to the repositories.
     */
    public void addRepositories(Set<String> repositories)
    {
        for (String repository : repositories)
        {
            addRepository(repository);
        }
    }

    /**
     * Add a repository which is not associated with a particular storage,
     *
     * @param repository The absolute path to the repository
     */
    public void addRepository(String repository)
    {
        Storage anonymousStorage = new Storage();
        anonymousStorage.addRepository(repository);

        addStorage(null, anonymousStorage);
    }

    /**
     * NOTE: This will currently not handle the case where more
     *       than one storage contains a repository with the same name.
     *
     * @param repository
     * @return
     */
    public List<Storage> getStoragesContainingRepository(String repository)
    {
        List<Storage> matchingStorages = new ArrayList<Storage>();

        for (Map.Entry entry : storages.entrySet())
        {
            Storage storage = (Storage) entry.getValue();
            if (storage.getRepositories().contains(repository))
            {
                matchingStorages.add(storage);
            }
        }

        return matchingStorages;
    }

    /**
     * @param storageName If the storage is anonymous, pass null as an argument.
     * @param storage
     */
    public void addStorage(String storageName, Storage storage)
    {
        if (storageName == null)
        {
            storageName = STORAGE_PREFIX + "-" + anonymousStoragesCounter.incrementAndGet();
        }

        storages.put(storageName, storage);
    }

    public void addStorages(Collection<Storage> storages)
    {
        for (Storage storage : storages)
        {
            addStorage(storage.getBasedir(), storage);
        }
    }

    public void removeStorage(String storageName)
    {
        storages.remove(storageName);
    }

    public Map<String, Storage> getStorages()
    {
        return storages;
    }

    public void setStorages(Map<String, Storage> storages)
    {
        this.storages = storages;
    }

}

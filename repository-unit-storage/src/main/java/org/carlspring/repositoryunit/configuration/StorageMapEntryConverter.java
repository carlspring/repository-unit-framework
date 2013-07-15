package org.carlspring.repositoryunit.configuration;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.carlspring.repositoryunit.storage.Storage;
import org.carlspring.repositoryunit.storage.repository.Repository;

import java.util.*;

/**
 * @author mtodorov
 */
public class StorageMapEntryConverter
        implements Converter
{

    public boolean canConvert(Class clazz)
    {
        return AbstractMap.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value,
                        HierarchicalStreamWriter writer,
                        MarshallingContext context)
    {
        //noinspection unchecked
        Map<String, Storage> map = (LinkedHashMap<String, Storage>) value;

        for (String key : map.keySet())
        {
            Storage storage = map.get(key);

            writer.startNode("storage");
            writer.startNode("basedir");
            writer.setValue(storage.getBasedir());
            writer.endNode();

            for (String repositoryKey : storage.getRepositories().keySet())
            {
                Repository repository = storage.getRepositories().get(repositoryKey);
                writeRepositoryNode(writer, repository);
            }

            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context)
    {
        Map<String, Storage> map = new HashMap<String, Storage>();

        while (reader.hasMoreChildren())
        {
            reader.moveDown();

            final String nodeName = reader.getNodeName();
            if (nodeName.equals("storage"))
            {
                final Iterator attributeNames = reader.getAttributeNames();

                Storage storage = new Storage();
                while (attributeNames.hasNext())
                {
                    Repository repository = (Repository) context.convertAnother(storage, Repository.class);
                    storage.addRepository(repository);
                }

                map.put(storage.getBasedir(), storage);
            }

            reader.moveUp();
        }

        return map;
    }

    private void writeRepositoryNode(HierarchicalStreamWriter writer,
                                     Repository repository)
    {
        writer.startNode("repository");
        writer.addAttribute("name", repository.getName());
        writer.addAttribute("layout", repository.getLayout());
        writer.addAttribute("policy", repository.getPolicy());
        writer.addAttribute("type", repository.getType());
        writer.endNode();
    }

}
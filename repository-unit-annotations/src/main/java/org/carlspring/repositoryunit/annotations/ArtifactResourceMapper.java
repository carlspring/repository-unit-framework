package org.carlspring.repositoryunit.annotations;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mtodorov
 */
public class ArtifactResourceMapper
{

    // Key:     groupId:artifactId
    // Sub-key: version
    // Entry:   resource information

    private static final Map<String, Map<String, ArtifactResource>> artifactResources = new LinkedHashMap<String, Map<String, ArtifactResource>>();


    public static void addResource(ArtifactResource resource)
    {
        final String ga = resource.groupId() + ":" + resource.artifactId();
        final Map<String, ArtifactResource> entryMap = artifactResources.get(ga);

        if (entryMap == null)
        {
            Map<String, ArtifactResource> resourceMap = new LinkedHashMap<String, ArtifactResource>();
            resourceMap.put(resource.version(), resource);

            artifactResources.put(ga, resourceMap);
        }
        else
        {
            if (!entryMap.containsKey(resource.version()))
            {
                entryMap.put(resource.version(), resource);
            }
        }
    }

    public static ArtifactResource getResource(String groupId, String artifactId, String version)
    {
        final String ga = groupId + ":" + artifactId;
        if (artifactResources.containsKey(ga))
        {
            return artifactResources.get(ga).get(version);
        }
        else
        {
            return null;
        }
    }

    public static void removeResources(String groupId, String artifactId, String version)
    {
        final String ga = groupId + ":" + artifactId;
        if (artifactResources.containsKey(ga))
        {
            artifactResources.get(ga).remove(version);
        }
    }

}

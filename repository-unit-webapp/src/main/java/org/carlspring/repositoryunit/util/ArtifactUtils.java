package org.carlspring.repositoryunit.util;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.versioning.VersionRange;

/**
 * @author mtodorov
 */
public class ArtifactUtils
{


    public static boolean isMetadata(String path)
    {
        return path.endsWith(".pom") || path.endsWith(".xml");
    }

    public static boolean isChecksum(String path)
    {
        return path.endsWith(".sha1") || path.endsWith(".md5");
    }

    public static boolean isArtifact(String path)
    {
        return !isMetadata(path) && !isChecksum(path);
    }

    public static Artifact convertPathToArtifact(String path)
    {
        String groupId = "";
        String version;
        String scope = "compile";
        String classifier = "";
        String type = path.substring(path.lastIndexOf(".") + 1, path.length());

        String[] groupIdElements = path.split("/");
        for (int i = 0; i < (groupIdElements.length - 3); i++)
        {
            if (groupId.length() == 0)
            {
                groupId += groupIdElements[i];
            }
            else
            {
                groupId += "." + groupIdElements[i];
            }
        }

        String[] split = path.substring(path.lastIndexOf("/") + 1, path.length() - 4).split("-");

   		/* Parse the artifactId */
        StringBuilder artifactId = new StringBuilder();
        int i = 0;
        for (; i < split.length; i++)
        {
            String token = split[i];
            try
            {
                Integer.parseInt(token.substring(0, 1));
                break;
            }
            catch (NumberFormatException e)
            {
                // This is okay, as we still haven't reached the version.
            }

            if (artifactId.length() > 0)
            {
                artifactId.append("-");
            }

            artifactId.append(token);
        }

   		/* Parse the artifactId */

   		/* Parse the version */

        version = split[i];

        // If the version starts with a number, all is fine.
        Integer.parseInt(version.substring(0, 1));

        // TODO: Not checking for number format exception

        i++;

        // Check if the version is a SNAPSHOT and append it, if it is.
        if ((i < split.length) && split[i].equals("SNAPSHOT"))
        {
            version += "-" + split[i];
            i++;
        }

   		/* Parse the version */

   		/* Parse the classifier, if any */
        if (i == (split.length - 1))
        {
            classifier = split[i];
        }
        /* Parse the classifier, if any */

        return new DefaultArtifact(groupId,
                                   artifactId.toString(),
                                   VersionRange.createFromVersion(version),
                                   scope,
                                   type,
                                   classifier,
                                   new DefaultArtifactHandler(type));
    }

}

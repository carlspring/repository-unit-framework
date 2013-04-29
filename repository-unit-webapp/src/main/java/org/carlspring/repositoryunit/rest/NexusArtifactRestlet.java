package org.carlspring.repositoryunit.rest;

import org.apache.maven.artifact.Artifact;
import org.carlspring.repositoryunit.annotations.ArtifactResource;
import org.carlspring.repositoryunit.annotations.ArtifactResourceMapper;
import org.carlspring.repositoryunit.util.ArtifactUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Random;

/**
 * @author Martin Todorov
 */
@Path("/nexus/content/repositories")
public class NexusArtifactRestlet
        extends BaseRestlet
{

    private static final Logger logger = LoggerFactory.getLogger(NexusArtifactRestlet.class);


    @PUT
    @Path("/{path:.*}")
    public void upload(@PathParam("path") String path,
                       @Context HttpHeaders headers,
                       byte[] in)
            throws IOException
    {
        System.out.println("PUT: " + path);
        logger.debug("PUT: " + path);
    }

    @GET
    @Path("/{path:.*}")
    public String download(@PathParam("path") String path)
            throws IOException
    {
        System.out.println("GET: " + path);
        logger.debug("GET: " + path);

        String repository = path.split("/")[0];
        String artifactPath = path.substring(path.indexOf("/") + 1, path.length());

        if (!ArtifactUtils.isArtifact(artifactPath))
        {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Artifact artifact = ArtifactUtils.convertPathToArtifact(artifactPath);

        ArtifactResource resource = ArtifactResourceMapper.getResource(artifact.getGroupId(),
                                                                       artifact.getArtifactId(),
                                                                       artifact.getVersion());

        if (resource == null)
        {
            logger.debug("Artifact " + artifact.toString() + " not found.");

            try
            {
                Thread.sleep(5000l);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        // NOTE: This is not meant for large artifacts

        return "Size: " + resource.length();
    }

    @DELETE
    @Path("/{path:.*}")
    public void delete(@PathParam("path") String path)
            throws IOException
    {
        System.out.println("DELETE: " + path);
        logger.debug("DELETE: " + path);
    }

}

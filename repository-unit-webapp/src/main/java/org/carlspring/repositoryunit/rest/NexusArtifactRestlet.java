package org.carlspring.repositoryunit.rest;

import org.apache.maven.artifact.Artifact;
import org.carlspring.repositoryunit.annotations.ArtifactResource;
import org.carlspring.repositoryunit.annotations.ArtifactResourceMapper;
import org.carlspring.repositoryunit.util.ArtifactUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
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

    private void generateRandomData(OutputStream out, long length)
            throws IOException
    {
        Random random = new Random();

        byte[] buffer = new byte[1];
        for (int i = 0; i < length; i++)
        {
            random.nextBytes(buffer);
            out.write(buffer);
            out.flush();
        }
    }

    @GET
    @Path("/{repository}/{path:.*}")
    public Response download(@PathParam("repository") String repository,
                             @PathParam("path") String artifactPath)
            throws IOException
    {
        if (!ArtifactUtils.isArtifact(artifactPath))
        {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Artifact artifact = ArtifactUtils.convertPathToArtifact(artifactPath);

        final ArtifactResource resource = ArtifactResourceMapper.getResource(artifact.getGroupId(),
                                                                             artifact.getArtifactId(),
                                                                             artifact.getVersion());

        if (resource == null)
        {
            logger.debug("Artifact " + artifact.toString() + " not found.");

            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        // Create random data using a ByteArrayOutputStream.
        // NOTE: This is not meant for large artifacts as it is.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        generateRandomData(baos, resource.length());

        System.out.println("Generated stream with " + baos.toByteArray().length + " bytes.");

        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        return Response.ok(is).build();
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

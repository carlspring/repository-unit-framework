package org.carlspring.repositoryunit.annotations;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.VersionRange;

/**
 * @author mtodorov
 */
public @interface ArtifactResource
{

    String repository();

    String groupId();

    String artifactId();

    String version();

    String type() default "jar";

    String classifier () default "";

    long length();

    ArtifactExistenceState state();

}

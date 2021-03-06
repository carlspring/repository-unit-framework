package org.carlspring.maven.repositoryunit;

/**
 * Copyright 2013 Martin Todorov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertiesResources;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;
import org.carlspring.repositoryunit.servers.jetty.JettyLauncher;

import java.io.File;
import java.io.IOException;

/**
 * @author mtodorov
 */
@PropertiesResources(resources = { "META-INF/properties/repository-unit-maven-plugin.properties" })
public abstract class AbstractRepositoryMojo
        extends AbstractMojo
{

    @Parameter (readonly = true, property = "project", required = true)
    public MavenProject project;

    @Parameter (property = "basedir")
    public String basedir;

    /**
     * The port to start the repository on.
     */
    @Parameter (property = "repository.unit.port", defaultValue = "48080")
    int port;

    /**
     * The current user system settings for use in Maven.
     */
    @Parameter (property = "settings", required = true, readonly = true)
    Settings settings;

    @PropertyValue (key = "repository.unit.version") // TODO: This may be redundant
    @Parameter (property = "repository.unit.version")
    String version;

    static JettyLauncher jettyLauncher;

    /**
     * The amount of seconds to wait before before timing out on a startup/shutdown.
     */
    @Parameter (property = "repository.unit.timeout", defaultValue = "15")
    long timeout;




    public void initializeJettyLauncher()
            throws IOException, InjectionException
    {
        PropertyValueInjector.inject(getImplementation());

        final String repositoryUnitBasedir = basedir + "/target/repository-unit";

        File repoDir = new File(repositoryUnitBasedir);
        //noinspection ResultOfMethodCallIgnored
        repoDir.mkdirs();

        final String war = settings.getLocalRepository() +
                           "/org/carlspring/strongbox/strongbox-webapp/" +
                           getVersion() +
                           "/strongbox-webapp-" + getVersion() + ".war";

        final File warFile = new File(war).getCanonicalFile();
        if (!warFile.exists())
        {
            throw new IOException("Failed to setup Jetty instance! Could not load WAR file '" + warFile.getCanonicalPath() +"'!");
        }

        jettyLauncher = new JettyLauncher(basedir);
        jettyLauncher.setBasedir(repositoryUnitBasedir);
        jettyLauncher.setWar(war);
    }

    protected abstract Object getImplementation();

    public MavenProject getProject()
    {
        return project;
    }

    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getBasedir()
    {
        return basedir;
    }

    public void setBasedir(String basedir)
    {
        this.basedir = basedir;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public JettyLauncher getJettyLauncher()
    {
        return jettyLauncher;
    }

    public void setJettyLauncher(JettyLauncher jettyLauncher)
    {
        this.jettyLauncher = jettyLauncher;
    }

    public long getTimeout()
    {
        return timeout;
    }

    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }

    public String getVersion()
    {
        return version;
    }

}

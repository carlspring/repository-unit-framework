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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jetty.server.Server;

/**
 * @author mtodorov
 * @goal   stop
 */
public class StopRepositoryMojo
        extends AbstractRepositoryMojo
{


    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            final Server server = getJettyLauncher().getServer();
            server.stop();

            getLog().info("Jetty status: " + server.getState());

            long total = 0;
            while (server.isStopping() || !server.isStopped())
            {
                Thread.sleep(250l);

                total += 250;
                if (total / 1000 >= timeout)
                {
                    throw new MojoExecutionException("Failed to stop Jetty within " + timeout + " seconds!");
                }
            }
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    @Override
    protected Object getImplementation()
    {
        return this;
    }

}

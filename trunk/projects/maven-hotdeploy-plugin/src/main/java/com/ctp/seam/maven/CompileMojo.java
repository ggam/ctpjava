
package com.ctp.seam.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Simple Mojo to execute the custom compile lifecycle
 * @author  Thomas Hug
 * 
 * @goal exploded
 * @execute lifecycle="hotdeploy" phase="compile"
 * @phase package
 * @requiresProject true
 */
public class CompileMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
    }

}

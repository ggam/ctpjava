/**
 * Copyright 2009 Thomas Hug.
 */

package com.ctp.seam.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Simple Mojo to execute the custom package lifecycle.
 * @author  Thomas Hug
 * 
 * @goal package
 * @execute lifecycle="hotdeploy" phase="prepare-package"
 * @phase prepare-package
 * @requiresProject true
 */
public class PackageMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
    }

}

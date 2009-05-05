/**
 * Copyright 2009 Thomas Hug.
 */
package com.ctp.seam.maven;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.ear.EarMojo;

/**
 * @author  Thomas Hug
 * 
 * @extendsPlugin ear
 * @extendsGoal ear
 * @goal ear-exploded
 * @execute lifecycle="hotdeploy" phase="package"
 * @phase package
 * @requiresProject true
 */
public class HotEarMojo extends EarMojo {
    
    /**
     * The directory the application gets deployed in. Not the app server directory,
     * directly the application directory containing the /WEB-INF directory.
     * @parameter
     * @required
     */
    private File deployDirectory;

    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        setWorkDirectory(deployDirectory);
        super.execute();
    }

    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------

    protected void performPackaging() throws MojoExecutionException {
    }

}

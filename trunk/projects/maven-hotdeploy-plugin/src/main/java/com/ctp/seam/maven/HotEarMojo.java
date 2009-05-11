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
 * @goal hot-ear
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
        if (supportsPackaging()) {
            setWorkDirectory(deployDirectory);
            super.execute();
        }
    }

    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------

    protected void performPackaging() throws MojoExecutionException {
    }
    
    protected boolean supportsPackaging() {
        return "ear".equals(getProject().getPackaging().toLowerCase());
    }

}

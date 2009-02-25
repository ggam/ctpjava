/**
 * Copyright 2008 Thomas Hug.
 */
package com.ctp.seam.maven;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.war.WarExplodedMojo;
import org.apache.maven.plugin.war.overlay.OverlayManager;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.packaging.WarProjectPackagingTask;
import org.apache.maven.plugin.war.util.WebappStructure;

import com.ctp.seam.maven.packaging.SeamWarPackagingContext;
import com.ctp.seam.maven.packaging.SeamWarProjectPackagingTask;

/**
 * @author  Thomas Hug
 * 
 * @extendsPlugin war
 * @extendsGoal exploded
 * @goal exploded
 * @phase package
 * @requiresProject true
 */
public class HotExplodedMojo extends WarExplodedMojo {
    
    /**
     * Where to put the hot deployable compiler output.
     * @parameter default-value="${project.build.directory}/hotdeploy"
     * @required
     */
    private File hotdeployOutputDirectory;
    
    // ------------------------------------------------------------------------
    // PUBLIC METHDOS
    // ------------------------------------------------------------------------

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHDOS
    // ------------------------------------------------------------------------

    protected WarPackagingContext createWarPackagingContext(
            File webappDirectory, WebappStructure cache,
            OverlayManager overlayManager, List defaultFilterWrappers,
            List nonFilteredFileExtensions) {
        WarPackagingContext ctx = super.createWarPackagingContext(
                webappDirectory, cache, overlayManager,
                defaultFilterWrappers, nonFilteredFileExtensions);
        return new SeamWarPackagingContext(ctx, hotdeployOutputDirectory);
    }

    protected WarProjectPackagingTask createWarPackagingTask() {
        return new SeamWarProjectPackagingTask(getWebResources(), getWebXml(), getContainerConfigXML());
    }

}

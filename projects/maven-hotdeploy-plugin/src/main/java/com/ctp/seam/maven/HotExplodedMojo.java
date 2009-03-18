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
     * @parameter default-value="${project.build.directory}/classes-hotdeploy"
     * @required
     */
    private File hotdeployOutputDirectory;
    
    /**
     * The directory the application gets deployed in. Not the app server directory,
     * directly the application directory containing the /WEB-INF directory.
     * @parameter
     * @required
     */
    private File deployDirectory;
    
    /**
     * Exclude classes present in WEB-INF/dev from being copied into WEB-INF/classes.
     * @parameter default-value="true"
     * @required
     */
    private boolean duplicateClassExclusion;
    
    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    public void execute() throws MojoExecutionException, MojoFailureException {
        setWebappDirectory(deployDirectory);
        super.execute();
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------

    protected WarPackagingContext createWarPackagingContext(
            File webappDirectory, WebappStructure cache,
            OverlayManager overlayManager, List defaultFilterWrappers,
            List nonFilteredFileExtensions) {
        WarPackagingContext ctx = super.createWarPackagingContext(
                webappDirectory, cache, overlayManager,
                defaultFilterWrappers, nonFilteredFileExtensions);
        return new SeamWarPackagingContext(ctx, hotdeployOutputDirectory, duplicateClassExclusion);
    }

    protected WarProjectPackagingTask createWarPackagingTask() {
        return new SeamWarProjectPackagingTask(getWebResources(), getWebXml(), 
                getContainerConfigXML());
    }

}

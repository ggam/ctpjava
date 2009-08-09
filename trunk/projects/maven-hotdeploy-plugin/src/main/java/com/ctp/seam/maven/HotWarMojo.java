/**
 * Copyright 2009 Thomas Hug.
 */

package com.ctp.seam.maven;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.war.WarMojo;
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
 * @extendsGoal war
 * @goal package
 * @phase package
 * @requiresProject true
 * @requiresDependencyResolution runtime
 */
public class HotWarMojo extends WarMojo {

    /**
     * Where to put the hot deployable compiler output.
     * @parameter default-value="${project.build.directory}/classes-hotdeploy"
     * @required
     */
    private File hotdeployOutputDirectory;
    
    /**
     * Exclude classes present in WEB-INF/dev from being copied into WEB-INF/classes.
     * @parameter default-value="true"
     * @required
     */
    private boolean duplicateClassExclusion;

    /**
     * The directory the application gets deployed in. Not the app server directory,
     * directly the application directory containing the /WEB-INF directory.
     * @parameter default-value="${project.build.directory}/${project.build.finalName}"
     * @required
     */
    private File deployDirectory;

    /**
     * Switch off hot deploy packaging by setting this to false.
     * @parameter default-value="false"
     * @required
     */
    private boolean useWarPackaging;
    
    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (supportsPackaging()) {
            if (!useWarPackaging)
                setWebappDirectory(deployDirectory);
            super.execute();
        }
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------
    
    protected boolean supportsPackaging() {
        String projectPackaging = getProject().getPackaging().toLowerCase();
        return "war".equals(projectPackaging) || "seam-war".equals(projectPackaging);
    }

    protected WarPackagingContext createWarPackagingContext(
            final File webappDirectory, final WebappStructure cache,
            final OverlayManager overlayManager, final List defaultFilterWrappers,
            final List nonFilteredFileExtensions) {
        WarPackagingContext ctx = super.createWarPackagingContext(
                webappDirectory, cache, overlayManager,
                defaultFilterWrappers, nonFilteredFileExtensions);
        if (useWarPackaging)
            return ctx;
        return new SeamWarPackagingContext(ctx, hotdeployOutputDirectory, duplicateClassExclusion);
    }

    protected WarProjectPackagingTask createWarPackagingTask() {
        if (useWarPackaging)
            return super.createWarPackagingTask();
        return new SeamWarProjectPackagingTask(getWebResources(), getWebXml(), 
                getContainerConfigXML());
    }
}

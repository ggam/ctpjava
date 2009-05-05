/**
 * Copyright 2009 Thomas Hug.
 */

package com.ctp.seam.maven;

import java.io.File;
import java.util.List;

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
 * @goal war
 * @execute lifecycle="hotdeploy" phase="compile"
 * @phase package
 * @requiresProject true
 */
public class HotPackagedMojo extends WarMojo {

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
    
    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------

    protected WarPackagingContext createWarPackagingContext(
            final File webappDirectory, final WebappStructure cache,
            final OverlayManager overlayManager, final List defaultFilterWrappers,
            final List nonFilteredFileExtensions) {
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

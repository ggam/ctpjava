/**
 * Copyright 2009 Thomas Hug.
 */
package com.ctp.seam.maven.packaging;

import java.io.File;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.packaging.WarProjectPackagingTask;

/**
 * @author  Thomas Hug
 */
public class SeamWarProjectPackagingTask extends WarProjectPackagingTask {

    public SeamWarProjectPackagingTask(Resource[] webResources, File webXml,
            File containerConfigXml) {
        super(webResources, webXml, containerConfigXml);
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHDOS
    // ------------------------------------------------------------------------
    
    protected void handleClassesDirectory(WarPackagingContext context)
            throws MojoExecutionException {
        HotClassesPackagingTask task = new HotClassesPackagingTask();
        task.performPackaging(context);
    }

}

/**
 * Copyright 2008 Thomas Hug.
 */
package com.ctp.seam.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.CompilerMojo;

/**
 * Adapt the default compiler mojo to be used for Seam hot deployment.
 * @author Thomas Hug
 *
 */
public class HotDeployMojo extends CompilerMojo {
    
    /**
     * Where to look for the hot deployable sources.
     */
    private String sourceDirectory;
    
    /**
     * Where to deploy the compiled sources to in the WAR file. 
     */
    private String deployPath;

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.CompilerMojo#getCompileSourceRoots()
     */
    protected List getCompileSourceRoots() {
        List roots = new ArrayList(1);
        roots.add(sourceDirectory);
        return roots;
    }

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.CompilerMojo#getOutputDirectory()
     */
    protected File getOutputDirectory() {
        File configured = super.getOutputDirectory();
        return new File(configured.getAbsolutePath() + deployPath);
    }

}

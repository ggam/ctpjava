/**
 * Copyright 2008 Thomas Hug.
 */
package com.ctp.seam.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.CompilerMojo;
import org.apache.maven.plugin.MojoExecutionException;

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
     * The directory the application gets deployed in. Not the app server directory,
     * directly the application directory containing the /WEB-INF directory.
     */
    private String deployDirectory;
    
    /**
     * Where to deploy the compiled sources to in the WAR file. 
     */
    private String deployPath;
    

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.CompilerMojo#execute()
     */
    public void execute() throws MojoExecutionException,
            CompilationFailureException {
        if (deployDirectory == null) {
            throw new MojoExecutionException("deployDirectory must be set.");
        }
        super.execute();
        cleanDeployDirectory();
    }

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

    
    /**
     * Old hot deployable files need to be cleaned for the changes to be picked up.
     */
    protected void cleanDeployDirectory() {
        File dir = new File(deployDirectory + File.separator + deployPath);
        getLog().debug("Cleaning " + dir.getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) {
            getLog().debug("Running delete on dir " + dir.getAbsolutePath());
            delete(dir, true);
        }
    }
    
    /**
     * Recursively delete a file tree.
     * @param file              File to be deleted, including the children.
     * @param skipFirst         The deploy folder should not be deleted.
     * @return                  {@code true} for success.
     */
    private boolean delete(File file, boolean skipFirst) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean couldDelete = delete(children[i], false);
                if (!couldDelete) {
                    getLog().warn("File " + children[i].getName() + " could not be deleted.");
                }
            }
        }
        if (skipFirst)
            return true;
        getLog().debug("Delete file " + file.getAbsolutePath());
        return file.delete();
    }

}

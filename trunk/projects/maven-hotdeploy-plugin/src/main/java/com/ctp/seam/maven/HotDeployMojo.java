/**
 * Copyright 2008 Thomas Hug.
 */
package com.ctp.seam.maven;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.CompilerMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Adapt the default compiler mojo to be used for Seam hot deployment.
 * @author Thomas Hug
 * 
 * @extendsPlugin compiler
 * @extendsGoal compile
 * @goal compile
 * @phase compile
 * @requiresProject true
 */
public class HotDeployMojo extends CompilerMojo {

    /**
     * Where to look for the hot deployable sources.
     * @parameter default-value="${project.build.sourceDirectory}"
     * @required
     */
    private String sourceDirectory;
    
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
    private String deployDirectory;
    
    /**
     * Where to deploy the compiled sources to in the WAR file. 
     * @parameter default-value="/WEB-INF/dev"
     * @required
     */
    private String deployPath;
    
    // ------------------------------------------------------------------------
    // PUBLIC METHDOS
    // ------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.CompilerMojo#execute()
     */
    public void execute() throws MojoExecutionException,
            CompilationFailureException {
        resetOutputDirectory();
        super.execute();
        cleanDeployDirectory();
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHDOS
    // ------------------------------------------------------------------------

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
        return new File(configured.getAbsolutePath());
    }

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.CompilerMojo#getClasspathElements()
     */
    protected List getClasspathElements() {
        List regular = super.getClasspathElements();
        regular.add(getOutputDirectory().getAbsolutePath());
        return regular;
    }

    /**
     * Old hot deployable files need to be cleaned for the changes to be picked up.
     */
    protected void cleanDeployDirectory() {
        File dir = new File(deployDirectory + File.separator + deployPath);
        getLog().debug("Cleaning " + dir.getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) {
            getLog().debug("Running clean on dir " + dir.getAbsolutePath());
            synchFolders(getOutputDirectory(), dir);
        }
    }
    
    /**
     * Overwrite the parent plugin's output directory.
     * @throws MojoExecutionException   Field is not accessible.
     */
    protected void resetOutputDirectory() throws MojoExecutionException {
        Field outputDirectory = null;
        Class clazz = getClass().getSuperclass();
        while (clazz != null) {
            try {
                outputDirectory = clazz.getDeclaredField("outputDirectory");
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        if (outputDirectory == null)
            throw new MojoExecutionException("Could not update outputDirectory, field not found");
        outputDirectory.setAccessible(true);
        try {
            outputDirectory.set(this, hotdeployOutputDirectory);
        } catch (Exception e) {
            throw new MojoExecutionException("Could not update outputDirectory", e);
        }

    }
    
    // ------------------------------------------------------------------------
    // PRIVATE METHDOS
    // ------------------------------------------------------------------------
    
    /**
     * Synchronize the content of two folders. Sync rules are:
     * - If the target content is not present in the source content, delete it
     * - If the target content file is older than the source file, delete it.
     * @param source            Source folder - not changed.
     * @param target            Target folder - changed according to sync rules.
     */
    private void synchFolders(File source, File target) {
        File[] deployed = target.listFiles();
        Map sourceContent = contentAsMap(source);
        for (int i = 0; i < deployed.length; i++) {
            String key = deployed[i].getName();
            if (!sourceContent.keySet().contains(key)) {
                delete(deployed[i]);
                return;
            }
            File compiled = (File) sourceContent.get(key);
            if (deployed[i].isFile() && compiled.lastModified() > deployed[i].lastModified()) {
                delete(deployed[i]);
                return;
            }
            if (compiled.isDirectory() && deployed[i].isDirectory()) {
                synchFolders(compiled, deployed[i]);
            }
        }
    }
    
    /**
     * Recursively delete a file tree.
     * @param file              File to be deleted, including the children.
     * @return                  {@code true} for success.
     */
    private boolean delete(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean couldDelete = delete(children[i]);
                if (!couldDelete) {
                    getLog().warn("File " + children[i].getName() + " could not be deleted.");
                }
            }
        }
        getLog().debug("Delete file " + file.getAbsolutePath());
        return file.delete();
    }
    
    /**
     * Helper method: Extract a content map from a directory with
     * the file name as key and the file itself as value.
     * @param dir               Directory to extract content.
     * @return                  Map instance.
     */
    private Map contentAsMap(File dir) {
        File[] content = dir.listFiles();
        Map result = new HashMap();
        for (int i = 0; i < content.length; i++) {
            result.put(content[i].getName(), content[i]);
        }
        return result;
    }

}

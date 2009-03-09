/**
 * Copyright 2009 Thomas Hug.
 */
package com.ctp.seam.maven.packaging;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.war.Overlay;
import org.apache.maven.plugin.war.packaging.ClassesPackagingTask;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.util.PathSet;

/**
 * @author  Thomas Hug
 */
public class HotClassesPackagingTask extends ClassesPackagingTask {
    
    public static final String HOT_CLASSES_PATH = "WEB-INF/dev/";
    
    // ------------------------------------------------------------------------
    // PUBLIC METHDOS
    // ------------------------------------------------------------------------

    public void performPackaging(WarPackagingContext context)
            throws MojoExecutionException {
        if (context instanceof SeamWarPackagingContext) {
            performHotPackaging((SeamWarPackagingContext) context);
            performClassPackaging((SeamWarPackagingContext) context);
        } else {
            super.performPackaging(context);
        }
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHDOS
    // ------------------------------------------------------------------------
    
    protected void performHotPackaging(SeamWarPackagingContext context) throws MojoExecutionException {
        if (context.getHotdeployOutputDirectory().exists()) {
            final PathSet hot = getFilesToIncludes(context.getHotdeployOutputDirectory(), null, null);
            try {
                copyFiles(Overlay.currentProjectInstance().getId(), context, context.getHotdeployOutputDirectory(),
                        hot, HOT_CLASSES_PATH, false);
            } catch ( IOException e ) {
                throw new MojoExecutionException(
                    "Could not copy webapp classes[" + context.getHotdeployOutputDirectory() + "]", e );
            }
        } else {
            context.getLog().warn("Directory " + context.getHotdeployOutputDirectory().getAbsolutePath()
                    + " does not exist. Call hotdeploy:compile before.");
        }
    }
    
    protected void performClassPackaging(SeamWarPackagingContext context) throws MojoExecutionException {
        String[] excludes = context.isDuplicateClassExclusion() ? prepareExcludes(context) : null;
        final PathSet main = getFilesToIncludes(context.getClassesDirectory(), null, excludes);
        try {
            copyFiles(Overlay.currentProjectInstance().getId(), context, context.getClassesDirectory(),
                    main, CLASSES_PATH, false);
        } catch ( IOException e ) {
            throw new MojoExecutionException(
                "Could not copy webapp classes[" + context.getClassesDirectory().getAbsolutePath() + "]", e );
        }
    }
    
    protected String[] prepareExcludes(SeamWarPackagingContext context) {
        File baseDir = context.getHotdeployOutputDirectory();
        Set result = new HashSet();
        if (baseDir.exists()) {
            addClassFiles(result, baseDir, baseDir);
            if (context.getLog().isDebugEnabled())
                context.getLog().debug("Source exclude patterns: " + result);
        }
        return (String[]) result.toArray(new String[] {});
    }
    
    protected void addClassFiles(Set result, File baseDir, File rootDir) {
        File[] children = baseDir.listFiles();
        boolean hasDirectories = hasDirectories(children);
        for (int i = 0; i < children.length; i++) {
            if (isClassFile(children[i]))
                result.add(extractPattern(children[i], rootDir, !hasDirectories));
            else if (children[i].isDirectory())
                addClassFiles(result, children[i], rootDir);
        }
    }
    
    // ------------------------------------------------------------------------
    // PRIVATE METHDOS
    // ------------------------------------------------------------------------
    
    private boolean isClassFile(File file) {
        return file.isFile() && file.getName().endsWith(".class");
    }
    
    private String extractPattern(File target, File rootDir, boolean useDirectoryPattern) {
        String rootDirName = rootDir.getAbsolutePath();
        String targetName = target.getAbsolutePath();
        String result = targetName.substring(rootDirName.length());
        if (useDirectoryPattern) {
            int lastFileSeparator = result.lastIndexOf(File.separator);
            if (lastFileSeparator > 0)
                result = result.substring(0, lastFileSeparator);
        }
        if (!"/".equals(File.separator))
            result = replaceToAntPattern(result);
        if (useDirectoryPattern)
            result += "**";
        return result;
    }
    
    private String replaceToAntPattern(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, File.separator);
        String result = "";
        while (tokenizer.hasMoreTokens()) {
            result += tokenizer.nextToken() + "/";
        }
        return result;
    }
    
    private boolean hasDirectories(File[] files) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                return true;
        }
        return false;
    }

}

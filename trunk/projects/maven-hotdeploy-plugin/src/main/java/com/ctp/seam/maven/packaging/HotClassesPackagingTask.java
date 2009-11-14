/**
 * Copyright 2009 Thomas Hug.
 */
package com.ctp.seam.maven.packaging;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    private Map directoriesVisited = new HashMap();
    
    // ------------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------------

    public void performPackaging(final WarPackagingContext context)
            throws MojoExecutionException {
        if (context instanceof SeamWarPackagingContext) {
            SeamWarPackagingContext seamContext = (SeamWarPackagingContext) context;
            if (preconditionsValid(seamContext)) {
                performHotPackaging(seamContext);
                performClassPackaging(seamContext);
            }
        } else {
            super.performPackaging(context);
        }
    }
    
    // ------------------------------------------------------------------------
    // PROTECTED METHODS
    // ------------------------------------------------------------------------

    protected boolean preconditionsValid(final SeamWarPackagingContext context) {
        File hotDeployClasses = context.getHotdeployOutputDirectory();
        if (!hotDeployClasses.exists() && context.isDuplicateClassExclusion()) {
            context.getLog().warn("Duplicate class filtering is active but this " +
            		"requires hot deployable classes to be compiled first " +
            		"[directory " + hotDeployClasses.getAbsolutePath() + " does not exist].");
            context.getLog().warn("Copy of classes to web module will be skipped.");
            return false;
        }
        return true;
    }

    protected void performHotPackaging(final SeamWarPackagingContext context) throws MojoExecutionException {
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
    
    protected void performClassPackaging(final SeamWarPackagingContext context) throws MojoExecutionException {
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
    
    protected String[] prepareExcludes(final SeamWarPackagingContext context) {
        File baseDir = context.getClassesDirectory();
        Set result = new HashSet();
        if (context.getHotdeployOutputDirectory().exists()) {
            addClassFiles(result, baseDir, context);
            if (context.getLog().isDebugEnabled())
                context.getLog().debug("Source exclude patterns: " + result);
        }
        return (String[]) result.toArray(new String[] {});
    }
    
    protected void addClassFiles(final Set result, final File baseDir, final SeamWarPackagingContext context) {
        if (!needsFilter(baseDir, context))
            return;
        File rootDir = context.getClassesDirectory();
        if (hotdeployFilesOnly(baseDir, context)) {
            result.add(extractWildcardPattern(baseDir, rootDir));
            return;
        }
        File[] children = baseDir.listFiles();
        for (int i = 0; i < children.length; i++) {
            if (children[i].isDirectory())
                addClassFiles(result, children[i], context);
            else if (isClassFile(children[i]) && existsInDev(children[i], context))
                result.add(extractPattern(children[i], rootDir));
        }
    }

    protected PathSet getFilesToIncludes(File baseDir, String[] includes, String[] excludes) {
        return super.getFilesToIncludes(baseDir, includes, excludes);
    }
    
    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------
    
    private boolean needsFilter(final File target, final SeamWarPackagingContext context) {
        String mainDirName = context.getClassesDirectory().getAbsolutePath();
        String targetName = target.getAbsolutePath();
        String result = targetName.substring(mainDirName.length());
        return fileExists(context.getHotdeployOutputDirectory(), result);
    }
    
    private boolean hotdeployFilesOnly(final File target, final SeamWarPackagingContext context) {
        if (visited(target))
            return visitedSuccess(target);
        String relativePath = relativePath(target, context.getClassesDirectory());
        File[] children = target.listFiles();
        for (int i = 0; i < children.length; i++) {
            if (children[i].isDirectory()) {
                boolean hotOnly = hotdeployFilesOnly(children[i], context);
                directoriesVisited.put(children[i].getAbsolutePath(), Boolean.valueOf(hotOnly));
                if (!hotOnly)
                    return false;
            }
            if (!new File(context.getHotdeployOutputDirectory()
                    + File.separator + relativePath 
                    + File.separator + children[i].getName()).exists())
                return false;
        }
        return true;
    }
    
    private boolean isClassFile(final File file) {
        return file.isFile() && file.getName().endsWith(".class");
    }
    
    private String extractWildcardPattern(final File target, final File rootDir) {
        String result = extractPattern(target, rootDir);
        result += "/**";
        return result;
    }
    
    private String extractPattern(final File target, final File rootDir) {
        String result = relativePath(target, rootDir);
        if (!"/".equals(File.separator))
            result = replaceToAntPattern(result);
        return pathSetFormat(result);
    }
    
    private String replaceToAntPattern(final String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, File.separator);
        String result = "";
        while (tokenizer.hasMoreTokens()) {
            result += tokenizer.nextToken() + (tokenizer.hasMoreTokens() ? "/" : "");
        }
        return result;
    }
    
    private String relativePath(final File target, final File rootDir) {
        String rootDirName = rootDir.getAbsolutePath();
        String targetName = target.getAbsolutePath();
        String result = targetName.substring(rootDirName.length());
        return result;
    }
    
    private boolean fileExists(final File dir, final String fileName) {
        if (!dir.isDirectory())
            return false;
        return new File(dir.getAbsolutePath() + File.separator + fileName).exists();
    }
    
    private boolean existsInDev(final File classFile, final SeamWarPackagingContext context) {
        String relative = relativePath(classFile, context.getClassesDirectory());
        return fileExists(context.getHotdeployOutputDirectory(), relative);
    }
    
    private boolean visited(final File file) {
        return directoriesVisited.containsKey(file.getAbsolutePath());
    }
    
    private boolean visitedSuccess(final File file) {
        return visited(file) && ((Boolean) directoriesVisited.get(file.getAbsolutePath())).booleanValue();
    }

    private String pathSetFormat(String in) {
        if (in != null && in.startsWith("/"))
            return in.substring(1);
        return in;
    }

}

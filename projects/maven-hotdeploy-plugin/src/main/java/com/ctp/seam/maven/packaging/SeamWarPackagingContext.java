/**
 * Copyright 2009 Thomas Hug.
 */
package com.ctp.seam.maven.packaging;

import java.io.File;
import java.util.List;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.util.WebappStructure;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

/**
 * @author  Thomas Hug
 */
public class SeamWarPackagingContext implements WarPackagingContext {
    
    private WarPackagingContext wrapped;
    private File hotdeployOutputDirectory;
    
    public SeamWarPackagingContext(WarPackagingContext wrapped, File hotdeployOutputDirectory) {
        this.wrapped = wrapped;
        this.hotdeployOutputDirectory = hotdeployOutputDirectory;
    }
    
    // ------------------------------------------------------------------------
    // PUBLIC METHDOS
    // ------------------------------------------------------------------------

    public File getHotdeployOutputDirectory() {
        return hotdeployOutputDirectory;
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#archiveClasses()
     */
    public boolean archiveClasses() {
        return wrapped.archiveClasses();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getArchive()
     */
    public MavenArchiveConfiguration getArchive() {
        return wrapped.getArchive();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getArchiverManager()
     */
    public ArchiverManager getArchiverManager() {
        return wrapped.getArchiverManager();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getClassesDirectory()
     */
    public File getClassesDirectory() {
        return wrapped.getClassesDirectory();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getFilterWrappers()
     */
    public List getFilterWrappers() {
        return wrapped.getFilterWrappers();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getFilters()
     */
    public List getFilters() {
        return wrapped.getFilters();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getJarArchiver()
     */
    public JarArchiver getJarArchiver() {
        return wrapped.getJarArchiver();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getLog()
     */
    public Log getLog() {
        return wrapped.getLog();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getMavenFileFilter()
     */
    public MavenFileFilter getMavenFileFilter() {
        return wrapped.getMavenFileFilter();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getOutputFileNameMapping()
     */
    public String getOutputFileNameMapping() {
        return wrapped.getOutputFileNameMapping();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getOverlaysWorkDirectory()
     */
    public File getOverlaysWorkDirectory() {
        return wrapped.getOverlaysWorkDirectory();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getOwnerIds()
     */
    public List getOwnerIds() {
        return wrapped.getOwnerIds();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getProject()
     */
    public MavenProject getProject() {
        return wrapped.getProject();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getWebappDirectory()
     */
    public File getWebappDirectory() {
        return wrapped.getWebappDirectory();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getWebappSourceDirectory()
     */
    public File getWebappSourceDirectory() {
        return wrapped.getWebappSourceDirectory();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getWebappSourceExcludes()
     */
    public String[] getWebappSourceExcludes() {
        return wrapped.getWebappSourceExcludes();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getWebappSourceIncludes()
     */
    public String[] getWebappSourceIncludes() {
        return wrapped.getWebappSourceIncludes();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#getWebappStructure()
     */
    public WebappStructure getWebappStructure() {
        return wrapped.getWebappStructure();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#isFilteringDeploymentDescriptors()
     */
    public boolean isFilteringDeploymentDescriptors() {
        return wrapped.isFilteringDeploymentDescriptors();
    }

    /**
     * @see org.apache.maven.plugin.war.packaging.WarPackagingContext#isNonFilteredExtension(java.lang.String)
     */
    public boolean isNonFilteredExtension(String fileName) {
        return wrapped.isNonFilteredExtension(fileName);
    }

}

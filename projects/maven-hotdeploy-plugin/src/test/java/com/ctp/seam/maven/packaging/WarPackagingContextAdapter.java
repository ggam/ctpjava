package com.ctp.seam.maven.packaging;

import java.io.File;
import java.util.List;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.util.WebappStructure;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

public class WarPackagingContextAdapter implements WarPackagingContext {

    public boolean archiveClasses() {
        return false;
    }

    public MavenArchiveConfiguration getArchive() {
        return null;
    }

    public ArchiverManager getArchiverManager() {
        return null;
    }

    public ArtifactFactory getArtifactFactory() {
        return null;
    }

    public File getClassesDirectory() {
        return null;
    }

    public List getFilterWrappers() {
        return null;
    }

    public List getFilters() {
        return null;
    }

    public JarArchiver getJarArchiver() {
        return null;
    }

    public Log getLog() {
        return null;
    }

    public MavenFileFilter getMavenFileFilter() {
        return null;
    }

    public String getOutputFileNameMapping() {
        return null;
    }

    public File getOverlaysWorkDirectory() {
        return null;
    }

    public List getOwnerIds() {
        return null;
    }

    public MavenProject getProject() {
        return null;
    }

    public File getWebappDirectory() {
        return null;
    }

    public File getWebappSourceDirectory() {
        return null;
    }

    public String[] getWebappSourceExcludes() {
        return null;
    }

    public String[] getWebappSourceIncludes() {
        // TODO Auto-generated method stub
        return null;
    }

    public WebappStructure getWebappStructure() {
        return null;
    }

    public boolean isFilteringDeploymentDescriptors() {
        return false;
    }

    public boolean isNonFilteredExtension(String fileName) {
        return false;
    }

}

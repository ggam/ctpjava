package com.ctp.seam.maven.packaging;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugin.war.packaging.WarPackagingContext;
import org.apache.maven.plugin.war.util.PathSet;

public class HotClassesPackagingTaskTest extends TestCase {
    
    private HotClassesPackagingTask task;

    public void testPrepareExcludes() {
        // given
        SeamWarPackagingContext context = prepareContext();
        String[] contains = new String[] {
                "common/A.class",
                "common/excludeall/**",
                "common/lvl1/**"
        };
        
        // when
        String[] result = task.prepareExcludes(context);
        
        // then
        Assert.assertNotNull(result);
        for (int i = 0; i < result.length; i++)
            System.out.println(result[i]);
        Assert.assertEquals(3, result.length);
        for (int i = 0; i < contains.length; i++)
            Assert.assertTrue(contains(result, contains[i]));
    }
    
    public void testPathSet() {
        // given
        SeamWarPackagingContext context = prepareContext();
        File baseDir = new File("target/test-classes/testdata/compiler");
        
        // when
        String[] result = task.prepareExcludes(context);
        PathSet set = task.getFilesToIncludes(baseDir, null, result);
        
        // then
        Assert.assertEquals(1, set.size());
    }

    protected void setUp() throws Exception {
        super.setUp();
        task = new HotClassesPackagingTask();
    }
    
    private boolean contains(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] != null && arr[i].equals(key))
                return true;
        return false;
    }

    private SeamWarPackagingContext prepareContext() {
        WarPackagingContext wrapped = new WarPackagingContextAdapter() {
            public File getClassesDirectory() {
                return new File("target/test-classes/testdata/compiler");
            }
            public Log getLog() {
                return new SystemStreamLog();
            }
        };
        File hot = new File("target/test-classes/testdata/hot");
        SeamWarPackagingContext result = new SeamWarPackagingContext(wrapped, hot, true);
        return result;
    }
}

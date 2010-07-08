package com.ctp.test.db;

import org.junit.rules.TestWatchman;

import org.junit.runners.model.FrameworkMethod;

import com.ctp.test.configuration.Configuration;
import com.ctp.test.configuration.DatabaseConfiguration;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public class DataHandlingRule extends TestWatchman {

    @Override
    public void starting(FrameworkMethod method) {
        PrepareData pc = method.getAnnotation(PrepareData.class);
        if (null == pc) {
            return;
        }
        
        DataSeeder dataSeeder = createDataSeeder(pc.value());
        try {
            dataSeeder.prepare();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finished(FrameworkMethod method) {
        PrepareData pc = method.getAnnotation(PrepareData.class);
        if (null == pc) {
            return;
        }
        
        DataSeeder dataSeeder = createDataSeeder(pc.value());
        
        try {
            dataSeeder.cleanup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private DataSeeder createDataSeeder(String fileName) {
        DatabaseConfiguration databaseConfiguration = Configuration.instance().getDatabaseConfiguration();
        DataSeeder dataSeeder = new XmlDatasetSeeder(fileName, databaseConfiguration);
        return dataSeeder;
    }
    
}

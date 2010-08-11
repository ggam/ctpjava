package com.ctp.test.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public final class PropertyLoader {

    private static final String SUFFIX = ".properties";

    public static Properties loadProperties (String propertyFilename, ClassLoader loader)  {
        if (null == propertyFilename) {
            throw new IllegalArgumentException ("null input: name");
        }
        
        propertyFilename = prepareFileName(propertyFilename);
        
        Properties result = null;
        InputStream in = null;
        
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader ();
        }
        
        try {
            in = loader.getResourceAsStream (propertyFilename);
            if (null != in) {
                result = new Properties();
                result.load(in); 
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to load required properties from file " + propertyFilename, e);
        } finally {
            if (null != in) {
                try { 
                    in.close (); 
                } catch (Throwable ignore) {
                    // do nothing
                }
            }
        }
        
        return result;
    }

    /**
     * Uses the current thread's context classloader for obtaining property file.
     */
    public static Properties loadProperties(String propertyFilename) {
        return loadProperties(propertyFilename, Thread.currentThread().getContextClassLoader());
    }

    private static String prepareFileName(String name) {

        if (name.startsWith ("/")) {
            name = name.substring (1);
        }

        if (name.endsWith(SUFFIX)) {
            name = name.substring(0, name.length() - SUFFIX.length());
        }
        
        name = name.replace ('.', '/');
        
        if (!name.endsWith(SUFFIX)) {
            name = name.concat(SUFFIX);
        }
        
        return name;
    }
        
}
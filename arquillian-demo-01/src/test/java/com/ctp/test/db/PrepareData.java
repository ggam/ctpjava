package com.ctp.test.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Seeds underlying database using dbunit's flat xml dataset.
 * After test execution all tables are cleaned up. 
 * 
 * @author Bartosz Majsak
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrepareData {
    
    String value();
    
}

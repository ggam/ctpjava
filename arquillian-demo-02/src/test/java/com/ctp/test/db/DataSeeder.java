package com.ctp.test.db;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public interface DataSeeder {

    void prepare();
    
    void cleanup();
    
}

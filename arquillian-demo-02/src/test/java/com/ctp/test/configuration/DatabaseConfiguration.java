package com.ctp.test.configuration;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public class DatabaseConfiguration {

    private final String url;
    
    private final String username;
    
    private final String password;
    
    private String initStatement = "";
    
    private String cleanupStatement = "";

    public DatabaseConfiguration(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getInitStatement() {
        return initStatement;
    }

    public void setInitStatement(String initStatement) {
        this.initStatement = initStatement;
    }

    public String getCleanupStatement() {
        return cleanupStatement;
    }

    public void setCleanupStatement(String cleanupStatement) {
        this.cleanupStatement = cleanupStatement;
    }
    
}

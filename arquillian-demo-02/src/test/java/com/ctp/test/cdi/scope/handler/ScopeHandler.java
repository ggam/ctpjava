package com.ctp.test.cdi.scope.handler;

public interface ScopeHandler {

    public void initializeContext();
    
    public void cleanupContext();
    
}

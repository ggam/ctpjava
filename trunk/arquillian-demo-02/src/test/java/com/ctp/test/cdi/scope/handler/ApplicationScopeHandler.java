package com.ctp.test.cdi.scope.handler;

import org.jboss.weld.Container;
import org.jboss.weld.context.AbstractApplicationContext;
import org.jboss.weld.context.ContextLifecycle;

import com.ctp.test.cdi.scope.HashMapBeanStore;


public class ApplicationScopeHandler implements ScopeHandler {

    @Override
    public void initializeContext() {
        AbstractApplicationContext context = Container.instance().services().get(ContextLifecycle.class).getApplicationContext();
        context.setBeanStore(new HashMapBeanStore());
        context.setActive(true);
    }

    @Override
    public void cleanupContext() {
        AbstractApplicationContext context = Container.instance().services().get(ContextLifecycle.class).getApplicationContext();
        if(context.isActive()) {
            context.setActive(false);
            context.cleanup();
        }
    }

}

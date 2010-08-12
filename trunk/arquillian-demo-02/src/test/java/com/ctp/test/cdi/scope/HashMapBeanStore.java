package com.ctp.test.cdi.scope;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.context.api.helpers.AbstractMapBackedBeanStore;

/**
 * 
 * Taken from Seam 3 examples : http://anonsvn.jboss.org/repos/seam/examples/trunk/
 *
 */
public class HashMapBeanStore extends AbstractMapBackedBeanStore implements Serializable {

    private static final long serialVersionUID = 956315549655640869L;
    
    protected Map<String, ContextualInstance<? extends Object>> delegate;

    public HashMapBeanStore() {
        delegate = new HashMap<String, ContextualInstance<? extends Object>>();
    }

    @Override
    protected Map<String, ContextualInstance<? extends Object>> delegate() {
        return delegate;
    }
}
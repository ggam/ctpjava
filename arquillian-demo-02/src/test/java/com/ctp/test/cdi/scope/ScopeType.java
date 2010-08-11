package com.ctp.test.cdi.scope;

import com.ctp.test.cdi.scope.handler.ApplicationScopeHandler;
import com.ctp.test.cdi.scope.handler.ConversationScopeHandler;
import com.ctp.test.cdi.scope.handler.RequestScopeHandler;
import com.ctp.test.cdi.scope.handler.ScopeHandler;
import com.ctp.test.cdi.scope.handler.SessionScopeHandler;
import com.ctp.test.cdi.scope.handler.SingletonScopeHandler;


public enum ScopeType {

    SINGLETON() {
        @Override
        public ScopeHandler getHandler() {
            return new SingletonScopeHandler();
        }
    },
    APPLICATION() {
        @Override
        public ScopeHandler getHandler() {
            return new ApplicationScopeHandler();
        }
    },
    CONVERSATION {
        @Override
        public ScopeHandler getHandler() {
            return new ConversationScopeHandler();
        }
    },
    SESSION {
        @Override
        public ScopeHandler getHandler() {
            return new SessionScopeHandler();
        }
    },
    REQUEST {
        @Override
        public ScopeHandler getHandler() {
            return new RequestScopeHandler();
        }
    };
    
    public abstract ScopeHandler getHandler();
    
}

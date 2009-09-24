package com.ctp.appengine;

import static org.jboss.seam.ScopeType.SESSION;

import java.security.Principal;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.NotLoggedInException;
import org.jboss.seam.security.SimplePrincipal;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Name("org.jboss.seam.security.identity")
@Scope(SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
public class AppEngineIdentity extends Identity {

    private static final long serialVersionUID = -9111123179634646677L;

    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";
    
    private transient UserService userService;
    
    @Create
    @Override
    public void create() {        
       userService = UserServiceFactory.getUserService();
    }
    
    @Override
    public boolean isLoggedIn() {
        return getUserService().isUserLoggedIn();
    }

    @Override
    public Principal getPrincipal() {
        if (isLoggedIn())
            return new SimplePrincipal(getUserService().getCurrentUser().getNickname());
        return null;
    }

    @Override
    public void checkRole(String role) {
        if (!isLoggedIn())
            throw new NotLoggedInException();
        if ((ROLE_ADMIN.equals(role) && !getUserService().isUserAdmin()) || !ROLE_USER.equals(role))
            throw new AuthorizationException(String.format(
                    "Authorization check failed for role [%s]", role));
    }

    @Override
    public boolean hasRole(String role) {
        if (!isLoggedIn())
            return false;
        return ((ROLE_ADMIN.equals(role) && getUserService().isUserAdmin()) || ROLE_USER.equals(role));
    }

    @Override
    public String getUsername() {
        if (isLoggedIn())
            return getUserService().getCurrentUser().getNickname();
        return null;
    }

    public String createLoginURL(String destination) {
        return getUserService().createLoginURL(destination);
    }
    
    public String createLogoutURL(String destination) {
        return getUserService().createLogoutURL(destination);
    }
    
    public User getUser() {
        if (isLoggedIn())
            return getUserService().getCurrentUser();
        return null;
    }
    
    private UserService getUserService() {
        if (userService == null)
            userService = UserServiceFactory.getUserService();
        return userService;
    }

}

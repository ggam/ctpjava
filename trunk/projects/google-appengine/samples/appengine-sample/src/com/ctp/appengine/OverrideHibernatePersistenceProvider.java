package com.ctp.appengine;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.persistence.PersistenceProvider;

@Name("org.jboss.seam.persistence.persistenceProvider")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
@Install(precedence = Install.APPLICATION, classDependencies={"org.hibernate.Session", "javax.persistence.EntityManager"})
public class OverrideHibernatePersistenceProvider extends PersistenceProvider {
}

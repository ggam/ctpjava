package com.ctp.seam.jpa2;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.persistence.HibernatePersistenceProvider;

/**
 * Overwrite the standard Hibernate persistence provider to add new JPA 2
 * capabilities.
 * 
 * @author Thomas Hug
 */
@Name("org.jboss.seam.persistence.persistenceProvider")
@Scope(ScopeType.STATELESS)
@BypassInterceptors
@Install(precedence = Install.APPLICATION, classDependencies={"org.hibernate.Session", "javax.persistence.EntityManager"})
public class HibernateJpa2PersistenceProvider extends HibernatePersistenceProvider {

    @Override
    public EntityManager proxyEntityManager(EntityManager entityManager) {
       return new Jpa2EntityManagerProxy(entityManager);
    }

}

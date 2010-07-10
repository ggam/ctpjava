package com.ctp.arquilliandemo.ex1.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ctp.arquilliandemo.ex1.domain.Share;
import com.ctp.arquilliandemo.ex1.domain.Share_;

@Stateless
public class ShareDao {

    @PersistenceContext
    EntityManager entityManager;
 
    
    public Share getByKey(String key) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Share> query = cb.createQuery(Share.class);
        Root<Share> root = query.from(Share.class);

        query.where(cb.equal(root.get(Share_.key), key));
        
        return entityManager.createQuery(query).getSingleResult();
    }
}

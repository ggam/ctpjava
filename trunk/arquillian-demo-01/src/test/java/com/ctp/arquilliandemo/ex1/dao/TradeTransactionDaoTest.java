package com.ctp.arquilliandemo.ex1.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ctp.arquilliandemo.ex1.domain.Share;
import com.ctp.arquilliandemo.ex1.domain.TradeTransaction;
import com.ctp.arquilliandemo.ex1.domain.User;
import com.ctp.test.UnderTest;
import com.ctp.test.db.DataHandlingRule;
import com.ctp.test.db.PrepareData;


/**
 * 
 * @author Bartosz Majsak
 *
 */
@RunWith(Arquillian.class)
public class TradeTransactionDaoTest {

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        return ShrinkWrap.create("test.jar", JavaArchive.class)
                         .addPackages(false, Share.class.getPackage())
                         .addClass(TradeTransactionDao.class)
                         .addManifestResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
                         .addManifestResource("inmemory-test-persistence.xml", ArchivePaths.create("persistence.xml"));
    }

    @Rule
    public DataHandlingRule dataHandlingRule = new DataHandlingRule();
    
    @PersistenceContext
    EntityManager em;
    
    @Inject @UnderTest
    TradeTransactionDao tradeTransactionDao;
    
    @Test
    @PrepareData("datasets/transactions.xml")
    public void shouldRetrieveTradeTransactionForUser() {
        // given
        User user = em.find(User.class, 1L);
        
        // when
        List<TradeTransaction> transactions = tradeTransactionDao.getTransactions(user); 
        
        // then
        assertThat(transactions).hasSize(1);
    }

}

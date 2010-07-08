package com.ctp.arquilliandemo.ex1.service;

import static org.fest.assertions.Assertions.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ctp.arquilliandemo.ex1.dao.TradeTransactionDao;
import com.ctp.arquilliandemo.ex1.domain.Share;
import com.ctp.arquilliandemo.ex1.domain.User;
import com.ctp.arquilliandemo.ex1.event.ShareEvent;
import com.ctp.test.UnderTest;
import com.ctp.test.db.DataHandlingRule;
import com.ctp.test.db.PrepareData;


/**
 * 
 * @author Bartosz Majsak
 *
 */
@RunWith(Arquillian.class)
public class TradeServiceTest {

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        
        return ShrinkWrap.create("test.war", WebArchive.class)
                         .addPackages(false, Share.class.getPackage(), ShareEvent.class.getPackage())
                         .addClass(TradeTransactionDao.class)
                         .addClass(TradeService.class)
                         .addWebResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
                         .addWebResource("inmemory-test-persistence.xml", "classes/META-INF/persistence.xml");
    }

    @Rule
    public DataHandlingRule dataHandlingRule = new DataHandlingRule();
    
    @PersistenceContext
    EntityManager em;
    
    @Inject @UnderTest
    TradeService tradeService;
    
    @Test
    @PrepareData("datasets/shares.xml")
    public void shouldAddShareToTheUserPortfolio() {
        // given
        User user = em.find(User.class, 1L);
        Share share = em.find(Share.class, 1L);
        Integer amount = Integer.valueOf(1);
        
        // when
        tradeService.buy(user, share, amount);
        
        // then
        assertThat(user.getSharesAmount(share)).isEqualTo(3);
    }
    
    @Test
    @PrepareData("datasets/shares.xml")
    public void shouldRemoveShareFromTheUserPortfolio() {
        // given
        User user = em.find(User.class, 1L);
        Share share = em.find(Share.class, 1L);
        Integer amount = Integer.valueOf(1);
        
        // when
        tradeService.sell(user, share, amount);
        
        // then
        assertThat(user.getSharesAmount(share)).isEqualTo(1);
    }

}

package com.ctp.arquilliandemo.ex1.dao;

import static org.fest.assertions.Assertions.assertThat;

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
import com.ctp.test.UnderTest;
import com.ctp.test.db.DataHandlingRule;
import com.ctp.test.db.PrepareData;

@RunWith(Arquillian.class)
public class ShareDaoTest {

    @Deployment
    public static Archive<?> createDeploymentPackage() {
        return ShrinkWrap.create("test.jar", JavaArchive.class)
                         .addPackages(false, Share.class.getPackage())
                         .addClass(ShareDao.class)
                         .addManifestResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
                         .addManifestResource("inmemory-test-persistence.xml", ArchivePaths.create("persistence.xml"));
    }

    @Rule
    public DataHandlingRule dataHandlingRule = new DataHandlingRule();
    
    @PersistenceContext
    EntityManager em;
    
    @Inject @UnderTest
    ShareDao shareDao;
    
    @Test
    @PrepareData("datasets/shares.xml")
    public void shouldRetrieveShareForGivenKey() {
        // given
        String ctpKey = "CTP";
        
        // when
        Share ctpShare = shareDao.getByKey(ctpKey);
        
        // then
        assertThat(ctpShare).isNotNull();
    }

    
}

package com.ctp.arquilliandemo.ex1.event;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.ctp.arquilliandemo.ex1.dao.TradeTransactionDao;
import com.ctp.arquilliandemo.ex1.domain.TradeTransaction;
import com.ctp.arquilliandemo.ex1.domain.TransactionType;

/**
 * 
 * @author Bartosz Majsak
 *
 */
@Singleton
public class TradeTransactionObserver implements Serializable {

    private static final long serialVersionUID = -7956754109902696136L;

    @Inject
    TradeTransactionDao tradeTransactionDao;
    
    public void shareSold(@Observes @Sell ShareEvent event) {
        TradeTransaction tradeTransaction = new TradeTransaction(event.getUser(), event.getShare(), event.getAmount(), TransactionType.BUY);
        tradeTransactionDao.save(tradeTransaction);
    }
    
    public void shareBought(@Observes @Buy ShareEvent event) {
        TradeTransaction tradeTransaction = new TradeTransaction(event.getUser(), event.getShare(), event.getAmount(), TransactionType.BUY);
        tradeTransactionDao.save(tradeTransaction);
    }
    
}

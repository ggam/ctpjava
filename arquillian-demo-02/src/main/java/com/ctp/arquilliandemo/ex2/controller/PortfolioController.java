package com.ctp.arquilliandemo.ex2.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ctp.arquilliandemo.ex2.domain.Share;
import com.ctp.arquilliandemo.ex2.domain.User;
import com.ctp.arquilliandemo.ex2.domain.User.LoggedIn;
import com.ctp.arquilliandemo.ex2.service.TradeService;

@ConversationScoped @Named("portfolioController")
public class PortfolioController implements Serializable {
    
    private static final long serialVersionUID = -3304517738187380600L;

    Map<Share, Integer> sharesToBuy = new HashMap<Share, Integer>();

    @Inject @LoggedIn
    User currentUser;

    @Inject
    private TradeService tradeService;
    
    @Inject
    private Conversation conversation;
    
    public void buy(Share share, Integer amount) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        Integer currentAmount = sharesToBuy.get(share);
        if (null == currentAmount) {
            currentAmount = Integer.valueOf(0);
        }
        
        sharesToBuy.put(share, currentAmount + amount); 
    }

    public void confirm() {
        for (Map.Entry<Share, Integer> sharesAmount : sharesToBuy.entrySet()) {
            tradeService.buy(currentUser, sharesAmount.getKey(), sharesAmount.getValue());
        }
        conversation.end();
    }
    
    public void cancel() {
        sharesToBuy.clear();
        conversation.end();
    }
    
    public User getCurrentUser() {
        return currentUser;
    }

}

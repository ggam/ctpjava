package com.ctp.arquilliandemo.ex2.event;

import com.ctp.arquilliandemo.ex2.domain.Share;
import com.ctp.arquilliandemo.ex2.domain.User;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public class ShareEvent {

    private final Share share;
    private final Integer amount;
    private final User user;
    
    public ShareEvent(Share share, User user, Integer amount) {
        this.share = share;
        this.amount = amount;
        this.user = user;
    }
    
    public Share getShare() {
        return share;
    }

    public Integer getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

}

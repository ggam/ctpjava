package com.ctp.arquilliandemo.ex1.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Bartosz Majsak
 *
 */
@Entity
public class TradeTransaction  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    @NotNull
    @ManyToOne
    private Share share;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    @Basic
    private Integer amount;

    protected TradeTransaction() {
        // required by JPA
    }

    public TradeTransaction(User user, Share share, Integer amount, TransactionType type) {
        this.user = user;
        this.share = share;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return (Date) timestamp.clone();
    }

    void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}

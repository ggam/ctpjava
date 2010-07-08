package com.ctp.arquilliandemo.ex1.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Bartosz Majsak
 *
 */
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic
    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @Basic
    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @Basic
    @NotNull
    @Size(max = 128)
    private String firstname;

    @Basic
    @NotNull
    @Size(max = 128)
    private String lastname;

    @ElementCollection
    @CollectionTable(name="USER_SHARES")
    @Column(name="AMOUNT")
    @MapKeyJoinColumn(name="SHARE_ID")
    private Map<Share, Integer> shares = new HashMap<Share, Integer>();

    
    public void addShares(Share share, Integer amount) {
        Integer current = Integer.valueOf(0);
        if (shares.containsKey(share)) {
            current = shares.get(share);
        }
    
        shares.put(share, current + amount);
        
    }
    
    /**
     * Removes given amount of shares. If amount is greater then currently stored
     * value it will be set to 0.
     * 
     * @param share
     * @param amount
     */
    public void removeShares(Share share, Integer amount) {
        Integer current = Integer.valueOf(0);
        if (shares.containsKey(share)) {
            current = shares.get(share);
        }
        
        int newAmount = current - amount;
        if (newAmount < 0) {
            newAmount = 0;
        }
        shares.put(share, newAmount);
        
    }

    public Integer getSharesAmount(Share share) {
        if (shares.containsKey(share)) {
            return shares.get(share);
        }
        
        return Integer.valueOf(0);
        
    }
    
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Map<Share, Integer> getShares() {
        return Collections.unmodifiableMap(shares);
    }

    void setShares(Map<Share, Integer> shares) {
        this.shares = shares;
    }
    
}

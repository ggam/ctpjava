package com.ctp.arquilliandemo.ex2.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Bartosz Majsak
 *
 */
@Entity
public class Share  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String key;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    protected Share() {
        // required by JPA
    }

    public Share(String key, BigDecimal price) {
        this.key = key;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Share)) {
            return false;
        }

        Share other = (Share) obj;
        
        return key.equals(other.getKey());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}

package com.ctp.arquilliandemo.ex2.domain;

import java.math.BigDecimal;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Share.class)
public abstract class Share_ {

	public static volatile SingularAttribute<Share, Long> id;
	public static volatile SingularAttribute<Share, BigDecimal> price;
	public static volatile SingularAttribute<Share, String> key;

}


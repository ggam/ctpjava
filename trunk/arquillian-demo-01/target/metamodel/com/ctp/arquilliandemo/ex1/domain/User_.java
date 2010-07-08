package com.ctp.arquilliandemo.ex1.domain;

import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile MapAttribute<User, Share, Integer> shares;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> password;

}


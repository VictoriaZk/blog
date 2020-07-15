package com.leverx.blog.model.metamodel;

import com.leverx.blog.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> role;
	public static volatile SingularAttribute<User, String> last_name;
	public static volatile SingularAttribute<User, Date> created_at;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> first_name;
	public static volatile SingularAttribute<User, String> email;

	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String LAST_NAME = "last_name";
	public static final String CREATED_AT = "created_at";
	public static final String ID = "id";
	public static final String FIRST_NAME = "first_name";
	public static final String EMAIL = "email";

}


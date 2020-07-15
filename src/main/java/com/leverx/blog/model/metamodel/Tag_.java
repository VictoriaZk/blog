package com.leverx.blog.model.metamodel;

import com.leverx.blog.model.Tag;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ {

	public static volatile SingularAttribute<Tag, String> name;
	public static volatile SingularAttribute<Tag, Integer> id;

	public static final String NAME = "name";
	public static final String ID = "id";

}


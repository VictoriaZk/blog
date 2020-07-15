package com.leverx.blog.model.metamodel;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Tag;
import com.leverx.blog.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ {

	public static volatile SetAttribute<Article, Tag> tagSet;
	public static volatile SingularAttribute<Article, Date> updated_at;
	public static volatile SingularAttribute<Article, Date> created_at;
	public static volatile SingularAttribute<Article, Integer> id;
	public static volatile SingularAttribute<Article, String> text;
	public static volatile SingularAttribute<Article, String> title;
	public static volatile SingularAttribute<Article, User> user;
	public static volatile SingularAttribute<Article, String> status;

	public static final String TAG_SET = "tagSet";
	public static final String UPDATED_AT = "updated_at";
	public static final String CREATED_AT = "created_at";
	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String TITLE = "title";
	public static final String USER = "user";
	public static final String STATUS = "status";

}


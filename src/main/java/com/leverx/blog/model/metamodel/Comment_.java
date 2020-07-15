package com.leverx.blog.model.metamodel;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Comment;
import com.leverx.blog.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, Date> created_at;
	public static volatile SingularAttribute<Comment, Integer> id;
	public static volatile SingularAttribute<Comment, String> message;
	public static volatile SingularAttribute<Comment, User> user;
	public static volatile SingularAttribute<Comment, Article> article;

	public static final String CREATED_AT = "created_at";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String USER = "user";
	public static final String ARTICLE = "article";

}


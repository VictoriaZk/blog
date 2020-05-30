package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    public static final String SELECT_A_TITLE_FROM_ARTICLE_A_WHERE_A_TITLE_TITLE = "SELECT a.title from Article a where a.title = :title";
    public static final String SELECT_ID_TITLE_TEXT_STATUS_AUTHOR_ID_UPDATED_AT_CREATED_AT_FROM_ARTICLE = "SELECT id, title, text, status, author_id, updated_at, created_at from Article";
    @PersistenceContext
    EntityManager entityManager;

    public ArticleRepositoryImpl() {
    }


    @Override
    public Optional<Article> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Article.class, id));
    }


    @Override
    public Integer create(Article article) {
        entityManager.persist(article);
        return article.getId();
    }

    @Override
    public Article update(Article article) {
        return entityManager.merge(article);
    }

    @Override
    public void delete(Integer id) {
        Article reference = entityManager.getReference(Article.class, id);
        entityManager.remove(reference);
    }

    /*not working*/
    @Override
    public List<Article> findAll() {
        Query query = entityManager.createQuery(SELECT_ID_TITLE_TEXT_STATUS_AUTHOR_ID_UPDATED_AT_CREATED_AT_FROM_ARTICLE);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public Optional<Article> findByName(String name) {
        Query query = entityManager.createQuery(SELECT_A_TITLE_FROM_ARTICLE_A_WHERE_A_TITLE_TITLE);
        query.setParameter("title", name);
        return (Optional.ofNullable((Article) query.getSingleResult()));
    }
}

package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ArticleRepositoryImpl implements ArticleRepository {
    private final Class<Article> entityClass;

    @PersistenceContext
    EntityManager entityManager;

    ArticleRepositoryImpl(Class<Article> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<Article> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    //bad
    @Override
    public Article create(Article article) {
        entityManager.persist(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        return entityManager.merge(article);
    }

    @Override
    public void delete(Integer id) {
        Article reference = entityManager.getReference(entityClass,id);
        entityManager.remove(reference);
    }

    @Override
    public List<ArticleDto> findAll() {
        return null;
    }
}

package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Status;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.service.pages.Page;
import com.leverx.blog.service.sort.ArticleSortProvider;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
@NoArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
    private static final String SELECT_A_FROM_ARTICLE_A_WHERE_A_STATUS_STATUS =
            "SELECT a from Article a where a.status = :status";
    private static final String SELECT_A_FROM_ARTICLE_A_WHERE_A_TITLE_NAME =
            "SELECT a from Article a where a.title = :name";
    private static final String SELECT_FROM_ARTICLE_TAG_WHERE_TAG_ID =
            "SELECT article_id FROM article_tag WHERE tag_id = ?";
    private static final String NAME = "name";
    private static final String STATUS = "status";

    @PersistenceContext
    EntityManager entityManager;

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

    @Override
    public List<Article> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> rootQuery = criteriaQuery.from(Article.class);
        criteriaQuery.select(rootQuery);
        TypedQuery<Article> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Article> findAllPublicArticles() {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_STATUS_STATUS);
        query.setParameter(STATUS, Status.PUBLIC.toString());
        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Article> findByName(String name) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_TITLE_NAME);
        query.setParameter(NAME, name);
        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Integer> findByTags(Integer id) {
        Query query = entityManager.createNativeQuery(SELECT_FROM_ARTICLE_TAG_WHERE_TAG_ID);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Article> findByStatus(String status) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_STATUS_STATUS);
        query.setParameter(STATUS, status);
        return query.getResultList();
    }


    @Override
    public List<Article> findAll(Page page, ArticleSortProvider articleSortProvider) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> root = criteriaQuery.from(Article.class);
        criteriaQuery.orderBy(articleSortProvider.getSortOrder(root, criteriaBuilder));
        Integer offset = page.getOffset();
        Integer limit = page.getLimit();
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }
}

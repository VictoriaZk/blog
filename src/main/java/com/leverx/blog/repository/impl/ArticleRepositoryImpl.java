package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> rootQuery = criteriaQuery.from(Article.class);
        criteriaQuery.select(rootQuery);
        TypedQuery<Article> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Article> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> rootQuery = criteriaQuery.from(Article.class);
        if (name != null) {

            EntityType<Article> entity = entityManager.getMetamodel().entity(Article.class);

            SingularAttribute<? super Article, ?> attribute = null;
            for (SingularAttribute<? super Article, ?> singleAttribute : entity.getSingularAttributes()) {
                // loop through all attributes that match this class
                if (singleAttribute.getName().equals("title")) {
                    // winner!
                    attribute = singleAttribute;
                    break;
                }
            }
            // where t.object = object.getID()
            criteriaQuery.where(criteriaBuilder.equal(rootQuery.get(attribute), name));
        }
        criteriaQuery.select(rootQuery);
        TypedQuery<Article> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Article> findByStatus(String status) {
        Query query = entityManager.createQuery("SELECT a from Article a where a.status = :status");
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Article> findAllSortByName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> root = criteriaQuery.from(Article.class);
        criteriaQuery.orderBy(new Order[]{criteriaBuilder.asc(root.get("title"))});
        /*new Predicate[]{criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), certificateName))}
        Predicate[] predicates = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        criteriaQuery.where(predicates);*/
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }
}

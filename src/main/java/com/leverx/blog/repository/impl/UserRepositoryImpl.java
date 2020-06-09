package com.leverx.blog.repository.impl;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.User;
import com.leverx.blog.repository.UserRepository;
import com.leverx.blog.service.pages.Page;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final String SELECT_U_FROM_USER_U_WHERE_U_EMAIL_EMAIL =
            "SELECT u FROM User u WHERE u.email =:email";
    private static final String SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_ID_USER_ID =
            "SELECT a FROM Article a WHERE a.user.id = :userId";
    private static final String SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_EMAIL_EMAIL =
            "SELECT a FROM Article a WHERE a.user.email = :email";
    private static final String EMAIL = "email";
    private static final String USER_ID = "userId";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Integer create(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<User> findByEmail(String email) {
        Query query = entityManager.createQuery(SELECT_U_FROM_USER_U_WHERE_U_EMAIL_EMAIL);
        query.setParameter(EMAIL, email);
        return query.getResultList();
    }

    @Override
    public void delete(Integer id) {
        User reference = entityManager.getReference(User.class, id);
        entityManager.remove(reference);
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Article> findUserArticles(String email, Page page) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_EMAIL_EMAIL);
        query.setParameter(EMAIL, email);
        Integer offset = page.getOffset();
        Integer limit = page.getLimit();
        return query
                .setFirstResult(offset * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Article> findUserArticles(Integer id, Page page) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_ID_USER_ID);
        query.setParameter(USER_ID, id);
        Integer offset = page.getOffset();
        Integer limit = page.getLimit();
        return query
                .setFirstResult(offset * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public Long amountOfUserArticles(String email) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_EMAIL_EMAIL);
        query.setParameter(EMAIL, email);
        return (long) query.getResultList().size();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public Long amountOfUserArticles(Integer id) {
        Query query = entityManager.createQuery(SELECT_A_FROM_ARTICLE_A_WHERE_A_USER_ID_USER_ID);
        query.setParameter(USER_ID, id);
        return (long) query.getResultList().size();
    }
}

package com.leverx.blog.repository.impl;

import com.leverx.blog.model.User;
import com.leverx.blog.repository.UserRepository;
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
    public static final String SELECT_U_FROM_USER_U_WHERE_U_EMAIL_EMAIL = "select u from User u where u.email =:email";
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

    @Override
    public List<User> findByEmail(String email) {
        Query query = entityManager.createQuery(SELECT_U_FROM_USER_U_WHERE_U_EMAIL_EMAIL);
        query.setParameter("email", email);
        return query.getResultList();
    }
}

package com.leverx.blog.repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);

    Integer create(User user);

    Optional<User> findByEmail(String email);

    void delete(Integer id);

    List<Article> findUserArticles(String email);

}

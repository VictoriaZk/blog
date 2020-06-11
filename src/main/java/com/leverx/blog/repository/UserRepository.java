package com.leverx.blog.repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.User;
import com.leverx.blog.service.pages.Page;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);

    Integer create(User user);

    Optional<User> findByEmail(String email);

    void delete(Integer id);

    List<Article> findUserArticles(String email);

    List<Article> findUserArticles(Integer id, Page page);

    Long amountOfUserArticles(String name);

    Long amountOfUserArticles(Integer id);
}

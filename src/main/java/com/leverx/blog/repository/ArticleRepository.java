package com.leverx.blog.repository;

import com.leverx.blog.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> findById(Integer id);

    Integer create(Article article);

    Article update(Article article);

    void delete(Integer id);

    List<Article> findAll();

    Optional<Article> findByName(String name);
}

package com.leverx.blog.repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Tag;
import com.leverx.blog.service.pages.Page;
import com.leverx.blog.service.sort.ArticleSortProvider;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> findById(Integer id);

    Integer create(Article article);

    Article update(Article article);

    void delete(Integer id);

    List<Article> findAll();

    List<Article> findAll(Page page, ArticleSortProvider articleSortProvider);

    List<Article> findAllPublicArticles();

    List<Article> findByStatus(String status);

    List<Article> findByName(String name);

    List<Article> findByTags(List<Tag> tags);
}

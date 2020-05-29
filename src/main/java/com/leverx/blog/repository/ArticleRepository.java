package com.leverx.blog.repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<Article>findById(Integer id);
    Article create(Article article);
    Article update(Article article);
    void delete(Integer id);
    List<ArticleDto> findAll();
}

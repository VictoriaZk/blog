package com.leverx.blog.service;


import com.leverx.blog.model.dto.ArticleDto;

import java.util.List;


public interface ArticleService {
    ArticleDto findById(Integer id);

    ArticleDto create(ArticleDto articleDto, String username);

    ArticleDto update(ArticleDto articleDto, String username);

    void remove(Integer id, String username);

    List<ArticleDto> findAll();

    List<ArticleDto> findAllPublicArticles();

    List<ArticleDto> findAll(Integer skip, Integer limit, String sort, String order);

    List<ArticleDto> findByStatus(String status);

    List<ArticleDto> findByName(String name);

    List<ArticleDto> findArticlesByTags(List<String> tags);

}

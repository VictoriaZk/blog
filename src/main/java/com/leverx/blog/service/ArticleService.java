package com.leverx.blog.service;


import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.TagDto;

import java.util.List;


public interface ArticleService {
    ArticleDto findById(Integer id);

    ArticleDto create(ArticleDto articleDto);

    ArticleDto update(ArticleDto articleDto);

    void remove(Integer id);

    List<ArticleDto> findAll();

    List<ArticleDto> findAllPublicArticles();

    List<ArticleDto> findAllSortByName();

    List<ArticleDto> findByStatus(String status);

    List<ArticleDto> findByName(String name);

    List<ArticleDto> findArticlesByTags(List<TagDto> tags);

}

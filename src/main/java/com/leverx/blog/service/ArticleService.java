package com.leverx.blog.service;


import com.leverx.blog.model.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    ArticleDto findById(Integer id);

    ArticleDto create(ArticleDto articleDto);

    ArticleDto update(ArticleDto articleDto);

    void remove(Integer id);

    List<ArticleDto> findAll();

}

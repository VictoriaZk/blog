package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.converter.ArticleDtoConverter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    public static final String THERE_IS_NO_ARTICLE_WITH_ID_S = "There is no article with id %s";
    ArticleDtoConverter articleDtoConverter;
    ArticleRepository articleRepository;

    @Transactional
    @Override
    public ArticleDto findById(Integer id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format(THERE_IS_NO_ARTICLE_WITH_ID_S, id)));
        return articleDtoConverter.convert(article);
    }

    @Transactional
    @Override
    public ArticleDto create(ArticleDto articleDto) {
        return null;
    }

    @Transactional
    @Override
    public ArticleDto update(ArticleDto articleDto) {
        Article article = articleDtoConverter.unconvert(articleDto);
        articleRepository.update(article);
        return findById(article.getId());
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        articleRepository.delete(id);
    }

    @Transactional
    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll();
    }
}

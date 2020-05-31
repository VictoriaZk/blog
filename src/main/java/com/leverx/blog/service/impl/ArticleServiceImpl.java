package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.converter.ArticleDtoConverter;
import com.leverx.blog.service.validation.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    public static final String THERE_IS_NO_ARTICLE_WITH_ID_S = "There is no article with id %s";
    private ArticleDtoConverter articleDtoConverter;
    private ArticleRepository articleRepository;
    private ArticleValidator articleValidator;

    @Autowired
    public ArticleServiceImpl(ArticleDtoConverter articleDtoConverter, ArticleRepository articleRepository,
                              ArticleValidator articleValidator) {
        this.articleDtoConverter = articleDtoConverter;
        this.articleRepository = articleRepository;
        this.articleValidator = articleValidator;
    }


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
        //articleValidator.validateUniqueArticleNameOnCreate(articleDto);
        Article article = articleDtoConverter.unconvert(articleDto);
        Integer articleId = articleRepository.create(article);
        return findById(articleId);
    }

    @Transactional
    @Override
    public ArticleDto update(ArticleDto articleDto) {
        //articleValidator.validateUniqueArticleNameOnUpdate(articleDto);
        Article article = articleDtoConverter.unconvert(articleDto);
        articleRepository.update(article);
        return findById(article.getId());
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        articleRepository
                .findById(id)
                .ifPresent(article -> articleRepository.delete(id));
    }

    @Transactional
    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> findByName(String name) {
        return articleRepository.findByName(name).stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> findByStatus(String status) {
        return articleRepository.findByStatus(status).stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ArticleDto> findAllSortByName() {
        return articleRepository.findAllSortByName().stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }
}

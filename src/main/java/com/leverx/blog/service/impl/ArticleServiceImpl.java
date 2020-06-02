package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Article;
import com.leverx.blog.model.Tag;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.converter.ArticleDtoConverter;
import com.leverx.blog.service.sorting.ArticleSortProvider;
import com.leverx.blog.service.specification.Specification;
import com.leverx.blog.service.specification.article.ArticleByTitle;
import com.leverx.blog.service.validation.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final String THERE_IS_NO_ARTICLE_WITH_ID_S = "There is no article with id %s";
    private ArticleDtoConverter articleDtoConverter;
    private ArticleRepository articleRepository;
    private ArticleValidator articleValidator;
    private TagRepository tagRepository;

    @Autowired
    public ArticleServiceImpl(ArticleDtoConverter articleDtoConverter, ArticleRepository articleRepository,
                              ArticleValidator articleValidator, TagRepository tagRepository) {
        this.articleDtoConverter = articleDtoConverter;
        this.articleRepository = articleRepository;
        this.articleValidator = articleValidator;
        this.tagRepository = tagRepository;
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
        articleValidator.validateUniqueArticleNameOnCreate(articleDto);
        Article article = articleDtoConverter.unconvert(articleDto);
        Set<Tag> attachedTags = new HashSet<>();
        article
                .getTagSet()
                .forEach(tag -> tagRepository.findAll().stream()
                        .map(attachedTags::add)
                        .findAny()
                        .orElseGet(() -> attachedTags.add(tag)));
        article.setTagSet(attachedTags);
        Integer articleId = articleRepository.create(article);
        return findById(articleId);
    }

    @Transactional
    @Override
    public ArticleDto update(ArticleDto articleDto) {
        articleValidator.validateUniqueArticleNameOnUpdate(articleDto);
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

    @Override
    public List<ArticleDto> findAllPublicArticles() {
        return articleRepository.findAllPublicArticles().stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findByName(String name) {
        return articleRepository.findByName(name).stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findArticlesByTags(List<String>tags) {
        articleRepository.findByTags(0);
        return null;
    }

    @Transactional
    @Override
    public List<ArticleDto> findByStatus(String status) {
        return articleRepository.findByStatus(status).stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findAllSortByName(String sortBy, String orderSort) {
        Specification<Article> articleSpecification = new ArticleByTitle(sortBy);
        return articleRepository.findAllSortByTitle(articleSpecification, new ArticleSortProvider(sortBy, orderSort))
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }
}

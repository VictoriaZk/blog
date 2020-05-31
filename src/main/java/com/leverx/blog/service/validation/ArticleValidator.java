package com.leverx.blog.service.validation;

import com.leverx.blog.exception.NameAlreadyExistException;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.stereotype.Component;


@Component
public class ArticleValidator {
    public static final String ARTICLE_NAME_ALREADY_EXIST = "CERTIFICATE_NAME_ALREADY_EXIST ";
    private ArticleRepository articleRepository;

    public ArticleValidator(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void validateUniqueArticleNameOnUpdate(ArticleDto articleDto) {
        articleRepository.findAll().stream()
                .map(article -> {
                    if (article.getTitle().equals(articleDto.getTitle()) &&
                            !article.getId().equals(articleDto.getId())) {
                        throw new NameAlreadyExistException(ARTICLE_NAME_ALREADY_EXIST + article.getTitle());
                    }
                    return article;
                });
    }

    public void validateUniqueArticleNameOnCreate(ArticleDto articleDto) {
        /*articleRepository.findByName(articleDto.getTitle())
                .ifPresent(article -> {
                    throw new NameAlreadyExistException(ARTICLE_NAME_ALREADY_EXIST + article.getTitle());
                });*/
    }


}

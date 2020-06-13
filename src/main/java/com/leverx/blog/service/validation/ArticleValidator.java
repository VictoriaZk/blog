package com.leverx.blog.service.validation;

import com.leverx.blog.exception.NameAlreadyExistException;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ArticleValidator {
    public static final String ARTICLE_NAME_ALREADY_EXIST = "Article title already exits! ";
    private ArticleRepository articleRepository;

    public void validateUniqueArticleNameOnUpdate(ArticleDto articleDto) {
        articleRepository
                .findAll()
                .stream()
                .map(article -> {
                    if (article.getTitle().equals(articleDto.getTitle()) &&
                            !article.getId().equals(articleDto.getId())) {
                        throw new NameAlreadyExistException(ARTICLE_NAME_ALREADY_EXIST + article.getTitle());
                    }
                    return article;
                });
    }

    public void validateUniqueArticleNameOnCreate(ArticleDto articleDto) {
        articleRepository.findByName(articleDto.getTitle())
                .stream()
                .findAny()
                .ifPresent(article -> {
                    throw new NameAlreadyExistException(ARTICLE_NAME_ALREADY_EXIST + article.getTitle());
                });
    }

}

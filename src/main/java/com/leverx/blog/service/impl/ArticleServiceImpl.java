package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Article;
import com.leverx.blog.model.Tag;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.converter.ArticleDtoConverter;
import com.leverx.blog.service.pages.PageImpl;
import com.leverx.blog.service.sort.ArticleSortProvider;
import com.leverx.blog.service.validation.ArticleValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private static final String THERE_IS_NO_ARTICLE_WITH_ID_S = "There is no article with id %s";
    private static final String ACCESS_DENIED = "Access denied";
    private final ArticleDtoConverter articleDtoConverter;
    private final ArticleRepository articleRepository;
    private final ArticleValidator articleValidator;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;


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
        articleDto.setCreated_at(new Date(System.currentTimeMillis()));
        articleDto.setUpdated_at(new Date(System.currentTimeMillis()));
        Article article = articleDtoConverter.unconvert(articleDto);
        Set<Tag> attachedTags = new HashSet<>();
        article
                .getTagSet()
                .forEach(tag -> {
                    tagRepository.findByName(tag.getName())
                            .map(attachedTags::add)
                            .orElseGet(() -> attachedTags.add(tagRepository.create(tag)));
                });
        article.setTagSet(attachedTags);
        Integer articleId = articleRepository.create(article);
        return findById(articleId);
    }

    @Transactional
    @Override
    public ArticleDto update(ArticleDto articleDto, String username) {
        ArticleDto articleDTO = findById(articleDto.getId());
        if (articleRepository
                .findById(articleDto.getId())
                .get()
                .getUser()
                .getEmail()
                .equals(username)) {
            articleValidator.validateUniqueArticleNameOnUpdate(articleDto);
            articleDTO.setStatus(articleDto.getStatus());
            articleDTO.setTitle(articleDto.getTitle());
            articleDTO.setUpdated_at(new Date(System.currentTimeMillis()));
            articleDTO.setText(articleDto.getText());
            Article article = articleDtoConverter.unconvert(articleDTO);
            Set<Tag> attachedTags = new HashSet<>();
            article
                    .getTagSet()
                    .forEach(tag -> {
                        tagRepository.findByName(tag.getName())
                                .map(attachedTags::add)
                                .orElseGet(() -> attachedTags.add(tagRepository.create(tag)));
                    });
            article.setTagSet(attachedTags);
            articleRepository.update(article);
            return findById(article.getId());
        } else {
            throw new ServiceException(ACCESS_DENIED);
        }
    }

    @Transactional
    @Override
    public void remove(Integer id, String username) {
        if (articleRepository
                .findById(id)
                .get()
                .getUser()
                .getEmail()
                .equals(username)) {
            commentRepository
                    .findAll(id)
                    .forEach(comment -> commentRepository.delete(comment.getId()));
            articleRepository
                    .findById(id)
                    .ifPresent(article -> articleRepository.delete(id));
        }
    }

    @Transactional
    @Override
    public List<ArticleDto> findAll() {
        return articleRepository
                .findAll()
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findAllPublicArticles() {
        return articleRepository
                .findAllPublicArticles()
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findByName(String name) {
        return articleRepository.
                findByName(name)
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    //bad
    @Transactional
    @Override
    public List<ArticleDto> findArticlesByTags(List<TagDto> tags) {
        /*return articleRepository
                .findByTags(tags)
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());*/
        return null;
    }

    @Transactional
    @Override
    public List<ArticleDto> findByStatus(String status) {
        return articleRepository
                .findByStatus(status)
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findAll(Integer skip, Integer limit, String sort, String order) {
        return articleRepository
                .findAll(new PageImpl(skip, limit), new ArticleSortProvider(sort, order))
                .stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }
}

package com.leverx.blog.service.converter;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleDtoConverter implements DtoConverter<Article, ArticleDto> {
    private UserRepository userRepository;
    private TagDtoConverter tagDtoConverter;

    public ArticleDtoConverter(UserRepository userRepository, TagDtoConverter tagDtoConverter) {
        this.userRepository = userRepository;
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public ArticleDto convert(Article article) {
        ArticleDto articleDto = new ArticleDto();
        if (article != null) {
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setText(article.getText());
            articleDto.setStatus(article.getStatus());
            articleDto.setAuthor_id(article.getUser().getId());
            articleDto.setCreated_at(article.getCreated_at());
            articleDto.setUpdated_at(article.getUpdated_at());
            Set<TagDto> tagDTO = article.getTagSet()
                    .stream()
                    .map(tagDtoConverter::convert)
                    .collect(Collectors.toSet());
            articleDto.setTags(tagDTO);
        }
        return articleDto;
    }

    @Override
    public Article unconvert(ArticleDto articleDto) {
        Article article = new Article();
        if (articleDto != null) {
            article.setId(articleDto.getId());
            article.setTitle(articleDto.getTitle());
            article.setText(articleDto.getText());
            article.setStatus(articleDto.getStatus());
            userRepository.findById(articleDto.getAuthor_id())
                    .ifPresent(article::setUser);
            article.setCreated_at(articleDto.getCreated_at());
            article.setUpdated_at(articleDto.getUpdated_at());
        }
        if (!articleDto.getTags().isEmpty()) {
            article.setTagSet(articleDto.getTags()
                    .stream()
                    .map(tagDtoConverter::unconvert)
                    .collect(Collectors.toSet()));
        }
        return article;
    }
}

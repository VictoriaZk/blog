package com.leverx.blog.service.converter;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.dto.ArticleDto;
import org.springframework.stereotype.Component;

@Component
public class ArticleDtoConverter implements DtoConverter<Article, ArticleDto> {
    @Override
    public ArticleDto convert(Article article) {
        ArticleDto articleDto = new ArticleDto();
        if(article != null){
           articleDto.setId(article.getId());
           articleDto.setTitle(article.getTitle());
           articleDto.setText(article.getText());
           articleDto.setAuthor_id(article.getAuthor_id());
           articleDto.setCreated_at(article.getCreated_at());
           articleDto.setUpdated_at(article.getUpdated_at());
        }
        return articleDto;
    }

    @Override
    public Article unconvert(ArticleDto articleDto) {
        Article article = new Article();
        if(articleDto != null){
            article.setId(articleDto.getId());
            article.setTitle(articleDto.getTitle());
            article.setText(articleDto.getText());
            article.setAuthor_id(articleDto.getAuthor_id());
            article.setCreated_at(articleDto.getCreated_at());
            article.setUpdated_at(articleDto.getUpdated_at());
        }
        return article;
    }
}

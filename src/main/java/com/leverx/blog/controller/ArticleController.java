package com.leverx.blog.controller;

import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") Integer id) {
        ArticleDto articleDto = articleService.findById(id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<ArticleDto>> getArticleByName(@PathVariable("name") String name) {
        List<ArticleDto> articles = articleService.findByName(name);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping(value = "/status/{status}")
    public ResponseEntity<List<ArticleDto>> getArticleByStatus(@PathVariable("status") String status) {
        List<ArticleDto> articles = articleService.findByStatus(status);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<ArticleDto>> getArticleBSortByName() {
        List<ArticleDto> articles = articleService.findAllSortByName();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        articleDto.setId(null);
        articleDto = articleService.create(articleDto);
        return new ResponseEntity<>(articleDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Integer id, @RequestBody ArticleDto articleDto) {
        ArticleDto articleDTO = articleService.findById(id);
        articleDTO.setStatus(articleDto.getStatus());
        articleDTO.setTitle(articleDto.getTitle());
        articleDTO.setText(articleDto.getText());
        articleDTO.setUpdated_at(new Date(System.currentTimeMillis()));
        articleDto = articleService.update(articleDTO);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticles() {
        List<ArticleDto> articles = articleService.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Integer id) {
        articleService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

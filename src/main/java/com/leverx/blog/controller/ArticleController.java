package com.leverx.blog.controller;

import com.leverx.blog.model.User;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.CommentDto;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") Integer id) {
        ArticleDto articleDto = articleService.findById(id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<ArticleDto>> getArticleBSortByName() {
        List<ArticleDto> articles = articleService.findAllSortByName();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticles() {
        List<ArticleDto> articles = articleService.findAllPublicArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping(value = "/my")
    public ResponseEntity<List<ArticleDto>> getAllArticles(Authentication authentication) {
        List<ArticleDto> articles = articleService.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        articleDto.setId(null);
        articleDto = articleService.create(articleDto);
        return new ResponseEntity<>(articleDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Integer id,
                                                    @RequestBody ArticleDto articleDto) {
        ArticleDto articleDTO = articleService.findById(id);
        articleDTO.setStatus(articleDto.getStatus());
        articleDTO.setTitle(articleDto.getTitle());
        articleDTO.setText(articleDto.getText());
        articleDTO.setUpdated_at(new Date(System.currentTimeMillis()));
        articleDto = articleService.update(articleDTO);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Integer id) {
        articleService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("id") Integer id) {
        List<CommentDto> commentsDto = commentService.findAll(id);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id1}/comments/{id2}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("id1") Integer articleId,
                                                 @PathVariable("id2") Integer commentId) {
        CommentDto commentDto = commentService.findById(articleId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> createArticle(@PathVariable("id") Integer articleId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        commentDto.setId(null);
        commentDto = commentService.create(articleId, commentDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping(value = "/{id1}/comments/{id2}")
    public ResponseEntity<CommentDto> deleteComment(Authentication authentication,
                                                    @PathVariable("id1") Integer articleId,
                                                    @PathVariable("id2") Integer commentId) {
        String userName = authentication.getName();
        commentService.remove(commentId, articleId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package com.leverx.blog.controller;

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
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getPublicArticles() {
        List<ArticleDto> articles = articleService.findAllPublicArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") Integer id) {
        ArticleDto articleDto = articleService.findById(id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/sort")
    public ResponseEntity<List<ArticleDto>> getSortArticles(
            @RequestParam(name = "skip", required = false, defaultValue = "1") Integer skip,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order) {
        List<ArticleDto> articles = articleService.findAll(skip, limit, sort, order);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/tags")
    public ResponseEntity<List<ArticleDto>> getArticlesByTags(
            @RequestParam(name = "tags", required = false) List<String> tags) {
        List<ArticleDto> articles = articleService.findArticlesByTags(tags);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/my")
    public ResponseEntity<List<ArticleDto>> getUserArticles(Authentication authentication) {
        String username = authentication.getName();
        List<ArticleDto> articles = userService.findUserArticles(username);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto,
                                                    Authentication authentication) {
        String username = authentication.getName();
        articleDto.setId(null);
        articleDto = articleService.create(articleDto, username);
        return new ResponseEntity<>(articleDto, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Integer id,
                                                    @RequestBody ArticleDto articleDto,
                                                    Authentication authentication) {
        String username = authentication.getName();
        articleDto.setId(id);
        articleDto = articleService.update(articleDto, username);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Integer id,
                                        Authentication authentication) {
        String username = authentication.getName();
        articleService.remove(id, username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("id") Integer id) {
        List<CommentDto> commentsDto = commentService.findAll(id);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/{id}/comments/sort")
    public ResponseEntity<List<CommentDto>> getCommentsSortByDate(
            @PathVariable("id") Integer id,
            @RequestParam(name = "skip", required = false, defaultValue = "1") Integer skip,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order
    ) {
        List<CommentDto> commentsDto = commentService.findAll(id, skip, limit, sort, order);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/{id1}/comments/{id2}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("id1") Integer articleId,
                                                 @PathVariable("id2") Integer commentId) {
        CommentDto commentDto = commentService.findById(articleId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Integer articleId,
                                                    @Valid @RequestBody CommentDto commentDto,
                                                    Authentication authentication) {
        String username = authentication.getName();
        commentDto.setId(null);
        commentDto = commentService.create(articleId, commentDto, username);
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

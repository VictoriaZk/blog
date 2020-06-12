package com.leverx.blog.repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.model.Comment;
import com.leverx.blog.service.pages.Page;
import com.leverx.blog.service.sort.ArticleSortProvider;
import com.leverx.blog.service.sort.CommentSortProvider;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Integer id);

    Optional<Comment> findById(Integer articleId, Integer commentId);

    Integer create(Comment comment);

    void delete(Integer id);

    List<Comment> findAll(Integer id);

    List<Comment> findAll(Integer id, Page page, CommentSortProvider commentSortProvider);
}

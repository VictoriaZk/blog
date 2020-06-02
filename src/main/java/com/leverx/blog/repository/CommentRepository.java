package com.leverx.blog.repository;

import com.leverx.blog.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Integer id);

    Optional<Comment> findById(Integer articleId, Integer commentId);

    Integer create(Comment comment);

    void delete(Integer id);

    List<Comment> findAll(Integer id);
}

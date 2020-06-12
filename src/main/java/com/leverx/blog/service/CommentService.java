package com.leverx.blog.service;

import com.leverx.blog.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(Integer id);

    CommentDto findById(Integer articleId, Integer commentId);

    CommentDto create(Integer id, CommentDto commentDto, String username);

    void remove(Integer commentId, Integer articleId, String username);

    List<CommentDto> findAll(Integer id);

}

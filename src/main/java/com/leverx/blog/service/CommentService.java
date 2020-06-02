package com.leverx.blog.service;

import com.leverx.blog.model.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto findById(Integer id);

    CommentDto findById(Integer articleId, Integer commentId);

    CommentDto create(Integer id, CommentDto commentDto);

    void remove(Integer id);

    List<CommentDto> findAll(Integer id);
}

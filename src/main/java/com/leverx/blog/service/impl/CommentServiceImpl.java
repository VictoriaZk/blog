package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Comment;
import com.leverx.blog.model.dto.CommentDto;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.converter.CommentDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String THERE_IS_NO_COMMENT_WITH_ID_S = "There is no comment with id %s ";
    private static final String CAN_NOT_CREATE_COMMENT = "Can not create comment!";
    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;

    @Transactional
    @Override
    public CommentDto findById(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format(THERE_IS_NO_COMMENT_WITH_ID_S, id)));
        return commentDtoConverter.convert(comment);
    }

    @Transactional
    @Override
    public CommentDto findById(Integer articleId, Integer commentId) {
        Comment comment = commentRepository.findById(articleId, commentId)
                .orElseThrow(() -> new ServiceException(String.format(THERE_IS_NO_COMMENT_WITH_ID_S, commentId)));
        return commentDtoConverter.convert(comment);
    }

    @Transactional
    @Override
    public CommentDto create(Integer articleId, CommentDto commentDto) {
        Comment comment = commentDtoConverter.unconvert(commentDto);
        if (comment.getArticle().getId().equals(articleId)) {
            Integer commentId = commentRepository.create(comment);
            return findById(commentId);
        } else {
            throw new ServiceException(CAN_NOT_CREATE_COMMENT);
        }
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        commentRepository
                .findById(id)
                .ifPresent(article -> commentRepository.delete(id));
    }

    @Transactional
    @Override
    public List<CommentDto> findAll(Integer articleId) {
        List<Comment> comments = commentRepository.findAll(articleId);
        return comments.stream()
                .map(commentDtoConverter::convert)
                .collect(Collectors.toList());
    }
}

package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Comment;
import com.leverx.blog.model.dto.CommentDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.repository.UserRepository;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.converter.CommentDtoConverter;
import com.leverx.blog.service.pages.PageImpl;
import com.leverx.blog.service.sort.ArticleSortProvider;
import com.leverx.blog.service.sort.CommentSortProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String THERE_IS_NO_COMMENT_WITH_ID_S = "There is no comment with id %s ";
    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

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

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    @Override
    public CommentDto create(Integer articleId, CommentDto commentDto, String username) {
        Comment comment = commentDtoConverter.unconvert(commentDto);
        comment.setUser(userRepository.findByEmail(username).get());
        comment.setArticle(articleRepository.findById(articleId).get());
        Integer commentId = commentRepository.create(comment);
        return findById(commentId);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    @Override
    public void remove(Integer commentId, Integer articleId, String username) {
        if (commentRepository
                .findById(commentId).get().getUser().getEmail()
                .equals(username)
                ||
                articleRepository
                        .findById(articleId).get().getUser().getEmail()
                        .equals(username)) {
            commentRepository
                    .findById(commentId)
                    .ifPresent(article -> commentRepository.delete(commentId));
        }
    }

    @Transactional
    @Override
    public List<CommentDto> findAll(Integer articleId) {
        List<Comment> comments = commentRepository.findAll(articleId);
        return comments.stream()
                .map(commentDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<CommentDto> findAll(Integer id, Integer skip, Integer limit, String sort, String order) {
        return commentRepository
                .findAll(id, new PageImpl(skip, limit), new CommentSortProvider(sort, order))
                .stream()
                .map(commentDtoConverter::convert)
                .collect(Collectors.toList());
    }

}

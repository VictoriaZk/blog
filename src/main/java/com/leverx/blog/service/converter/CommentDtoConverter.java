package com.leverx.blog.service.converter;

import com.leverx.blog.model.Comment;
import com.leverx.blog.model.dto.CommentDto;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter implements DtoConverter<Comment, CommentDto> {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public CommentDtoConverter(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        if (comment != null) {
            commentDto.setId(comment.getId());
            commentDto.setMessage(comment.getMessage());
            commentDto.setPost_id(comment.getArticle().getId());
            commentDto.setAuthor_id(comment.getUser().getId());
            commentDto.setCreated_at(comment.getCreated_at());
        }
        return commentDto;
    }

    @Override
    public Comment unconvert(CommentDto commentDto) {
        Comment comment = new Comment();
        if (commentDto != null) {
            comment.setId(commentDto.getId());
            comment.setMessage(commentDto.getMessage());
            articleRepository.findById(commentDto.getPost_id())
                    .ifPresent(comment::setArticle);
            userRepository.findById(commentDto.getAuthor_id())
                    .ifPresent(comment::setUser);
            comment.setCreated_at(commentDto.getCreated_at());
        }
        return comment;
    }
}

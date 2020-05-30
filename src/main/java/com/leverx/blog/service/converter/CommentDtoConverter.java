package com.leverx.blog.service.converter;

import com.leverx.blog.model.Comment;
import com.leverx.blog.model.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter implements DtoConverter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        if (comment != null) {
            commentDto.setId(comment.getId());
            commentDto.setMessage(comment.getMessage());
            commentDto.setPost_id(comment.getPost_id());
            commentDto.setAuthor_id(comment.getAuthor_id());
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
            comment.setPost_id(commentDto.getPost_id());
            comment.setAuthor_id(commentDto.getAuthor_id());
            comment.setCreated_at(commentDto.getCreated_at());
        }
        return comment;
    }
}

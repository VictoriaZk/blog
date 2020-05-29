package com.leverx.blog.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;

    private String message;

    private Integer post_id;

    private Integer author_id;

    private Date created_at;
}

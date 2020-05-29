package com.leverx.blog.model.dto;

import com.leverx.blog.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Integer id;

    private String title;

    private String text;

    private Status status;

    private Integer author_id;

    private Date created_at;

    private Date updated_at;
}

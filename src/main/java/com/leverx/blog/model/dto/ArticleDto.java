package com.leverx.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Integer id;

    private String title;

    private String text;

    private String status;

    private Integer author_id;

    private Date created_at;

    private Date updated_at;

    private Set<TagDto> tags = new HashSet<>();
}

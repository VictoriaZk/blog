package com.leverx.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    private List<T> pageData;
    @Min(value = 1, message = "page.min")
    private int page;
    private long pageCount;
    @Min(value = 1, message = "limit.min")
    private int limit;

}

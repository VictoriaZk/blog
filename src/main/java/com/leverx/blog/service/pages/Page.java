package com.leverx.blog.service.pages;

public interface Page {
    Integer getOffset();

    Integer getLimit();

    Long getAllPages(Long countOfElements, Integer limit);
}

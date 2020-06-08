package com.leverx.blog.service;

import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.PageDto;
import com.leverx.blog.model.dto.UserDto;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto findById(Integer id);

    void remove(Integer id);

    PageDto<ArticleDto> findUserArticles(Integer id, Integer currentPage, Integer pageSize);

    PageDto<ArticleDto> findUserArticles(String email, Integer currentPage, Integer pageSize);
}

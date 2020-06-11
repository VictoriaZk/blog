package com.leverx.blog.service;

import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.PageDto;
import com.leverx.blog.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto findById(Integer id);

    void remove(Integer id);

    UserDto findByEmail(String email);

    List<ArticleDto> findUserArticles(String email);

    PageDto<ArticleDto> findUserArticles(String email, Integer currentPage, Integer pageSize);
}

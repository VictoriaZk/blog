package com.leverx.blog.service;

import com.leverx.blog.model.dto.UserDto;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto findById(Integer id);
}

package com.leverx.blog.repository;

import com.leverx.blog.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);
}

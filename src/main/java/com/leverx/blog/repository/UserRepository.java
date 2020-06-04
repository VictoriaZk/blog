package com.leverx.blog.repository;

import com.leverx.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);

    Integer create(User user);

    List<User> findByEmail(String email);
}

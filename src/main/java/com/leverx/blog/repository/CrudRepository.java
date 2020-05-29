package com.leverx.blog.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Integer create(T entity);

    Optional<T> findById(Integer id);

    void delete(Integer id);

    T update(T entity);

    List<T> find();

}

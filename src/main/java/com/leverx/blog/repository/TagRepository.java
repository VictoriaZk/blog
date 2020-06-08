package com.leverx.blog.repository;

import com.leverx.blog.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Optional<Tag> findById(Integer id);

    Integer create(Tag tag);

    void delete(Integer id);

    List<Tag> findAll();

    void detachTagFromArticle(Integer id);
}

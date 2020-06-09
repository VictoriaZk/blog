package com.leverx.blog.repository;

import com.leverx.blog.model.Tag;
import com.leverx.blog.model.dto.TagDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagRepository {
    Optional<Tag> findById(Integer id);

    Integer create(Tag tag);

    void delete(Integer id);

    Optional<List<Tag>> findAll();

    void detachTagFromArticle(Integer id);

    Optional<Tag> findByName(String name);

    int amountOfArticlesWithGivenTag(Integer id);
}

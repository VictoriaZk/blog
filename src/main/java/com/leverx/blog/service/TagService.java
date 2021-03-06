package com.leverx.blog.service;

import com.leverx.blog.model.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService {
    TagDto findById(Integer id);

    TagDto create(TagDto tagDto);

    void remove(Integer id);

    List<TagDto> findAll();

    TagDto findByName(String name);

    Map<String, Integer> amountOfArticlesWithGivenTag(Integer id);

}

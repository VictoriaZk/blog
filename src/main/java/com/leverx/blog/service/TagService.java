package com.leverx.blog.service;

import com.leverx.blog.model.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagDto findById(Integer id);

    TagDto create(TagDto tagDto);

    void remove(Integer id);

    List<TagDto> findAll();
}

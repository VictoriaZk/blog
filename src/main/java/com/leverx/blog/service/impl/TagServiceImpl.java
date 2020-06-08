package com.leverx.blog.service.impl;

import com.leverx.blog.exception.ServiceException;
import com.leverx.blog.model.Tag;
import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.service.TagService;
import com.leverx.blog.service.converter.TagDtoConverter;
import com.leverx.blog.service.validation.TagValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private static final String THERE_IS_NO_TAG_WITH_ID_S = "There is no tag with id %s";
    private TagRepository tagRepository;
    private TagDtoConverter tagDtoConverter;
    private TagValidator tagValidator;


    @Transactional
    @Override
    public TagDto findById(Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format(THERE_IS_NO_TAG_WITH_ID_S, id)));
        return tagDtoConverter.convert(tag);
    }

    @Transactional
    @Override
    public TagDto create(TagDto tagDto) {
        tagValidator.validateUniqueATagName(tagDto);
        Tag tag = tagDtoConverter.unconvert(tagDto);
        Integer tagId = tagRepository.create(tag);
        return findById(tagId);
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        tagRepository
                .findById(id)
                .ifPresent(article -> tagRepository.delete(id));

    }

    @Transactional
    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().get().stream()
                .map(tagDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public TagDto findByName(String name) {
        return tagDtoConverter.convert(tagRepository.findByName(name).get());
    }


}

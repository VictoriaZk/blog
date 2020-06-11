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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private static final String THERE_IS_NO_TAG_WITH_ID_S = "There is no tag with id %s";
    private final TagRepository tagRepository;
    private final TagDtoConverter tagDtoConverter;
    private final TagValidator tagValidator;


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
        Tag creatingTag = tagRepository.create(tag);
        return findById(creatingTag.getId());
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
        return tagRepository
                .findAll()
                .stream()
                .map(tagDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TagDto findByName(String name) {
        return tagRepository
                .findByName(name)
                .map(tagDtoConverter::convert)
                .get();
    }

    @Transactional
    @Override
    public Map<String, Integer> amountOfArticlesWithGivenTag(Integer id) {
        Map<String, Integer> tags = new HashMap<>();
        Integer amountOfArticles = tagRepository.amountOfArticlesWithGivenTag(id);
        String name = tagRepository.findById(id).get().getName();
        tags.put(name, amountOfArticles);
        return tags;
    }

}

package com.leverx.blog.service.validation;

import com.leverx.blog.exception.NameAlreadyExistException;
import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.repository.TagRepository;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {
    public static final String TAG_NAME_ALREADY_EXIST = "Tag already exits! ";
    private TagRepository tagRepository;

    public TagValidator(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void validateUniqueATagName(TagDto tagDto) {
        tagRepository.findAll().stream()
                .map(tag -> {
                    if (tag.getName().equals(tagDto.getName()) &&
                            !tag.getId().equals(tagDto.getId())) {
                        throw new NameAlreadyExistException(TAG_NAME_ALREADY_EXIST + tag.getName());
                    }
                    return tag;
                });
    }
}

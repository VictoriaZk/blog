package com.leverx.blog.controller;

import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getTags() {
        List<TagDto> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> createArticle(@Valid @RequestBody TagDto tagDto) {
        tagDto.setId(null);
        tagDto = tagService.create(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteTag(@PathVariable("id") Integer id) {
        tagService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

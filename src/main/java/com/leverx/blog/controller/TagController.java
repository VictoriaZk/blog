package com.leverx.blog.controller;

import com.leverx.blog.model.dto.TagDto;
import com.leverx.blog.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/tags")
public class TagController {
    private TagService tagService;

    @GetMapping(value = "/cloud")
    public ResponseEntity<List<Map<String, Integer>>> getCloudOfTags(){
        List<TagDto> tags = tagService.findAll();
        List<Map<String, Integer>> tagsCloud = tags.stream()
                .map(tagDto -> tagService.amountOfArticlesWithGivenTag(tagDto.getId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagsCloud, HttpStatus.OK);
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

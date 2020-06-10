package com.leverx.blog.controller;

import com.leverx.blog.model.Role;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.PageDto;
import com.leverx.blog.model.dto.UserDto;
import com.leverx.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        userDto.setRole(Role.ROLE_USER.toString());
        UserDto newUser = userService.create(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/articles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<ArticleDto> findUserArticles(Authentication authentication,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "limit", required = false, defaultValue = "3") Integer limit) {
        String userName = authentication.getName();
        return userService.findUserArticles(userName, page, limit);
    }
}

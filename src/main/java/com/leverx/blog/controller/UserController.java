package com.leverx.blog.controller;

import com.leverx.blog.model.Role;
import com.leverx.blog.model.dto.UserDto;
import com.leverx.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        userDto.setRole(Role.USER.toString());
        userDto.setId(null);
        UserDto newUser = userService.create(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

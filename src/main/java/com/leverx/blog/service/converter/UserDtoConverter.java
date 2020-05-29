package com.leverx.blog.service.converter;

import com.leverx.blog.model.User;
import com.leverx.blog.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<User, UserDto> {
    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        if (user != null) {
            userDto.setId(user.getId());
            userDto.setFirst_name(user.getFirst_name());
            userDto.setLast_name(user.getLast_name());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setCreated_ac(user.getCreated_ac());
        }
        return userDto;
    }

    @Override
    public User unconvert(UserDto userDto) {
        User user = new User();
        if (userDto != null) {
            user.setId(userDto.getId());
            user.setFirst_name(userDto.getFirst_name());
            user.setLast_name(userDto.getLast_name());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setCreated_ac(userDto.getCreated_ac());
        }
        return user;
    }
}


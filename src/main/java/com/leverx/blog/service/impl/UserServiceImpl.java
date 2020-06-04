package com.leverx.blog.service.impl;

import com.leverx.blog.exception.NameAlreadyExistException;
import com.leverx.blog.exception.ObjectNotFoundException;
import com.leverx.blog.model.User;
import com.leverx.blog.model.dto.UserDto;
import com.leverx.blog.repository.UserRepository;
import com.leverx.blog.service.UserService;
import com.leverx.blog.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ID = "User with such id not found";
    private static final String USER_WITH_SUCH_Email_EXIST = "User with such email already exist";
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private PasswordEncoder encoder;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoConverter userDtoConverter,
                           PasswordEncoder encoder, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).stream().findAny()
                .ifPresent(user -> {
                    throw new NameAlreadyExistException(USER_WITH_SUCH_Email_EXIST
                            + user.getEmail());
                });
        User user = userDtoConverter.unconvert(userDto);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Integer userID = userRepository.create(user);
        return findById(userID);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userDtoConverter::convert)
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND_ID + id));
    }
}

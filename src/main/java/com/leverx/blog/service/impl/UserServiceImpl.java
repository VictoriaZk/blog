package com.leverx.blog.service.impl;

import com.leverx.blog.exception.NameAlreadyExistException;
import com.leverx.blog.exception.ObjectNotFoundException;
import com.leverx.blog.model.User;
import com.leverx.blog.model.dto.ArticleDto;
import com.leverx.blog.model.dto.PageDto;
import com.leverx.blog.model.dto.UserDto;
import com.leverx.blog.repository.UserRepository;
import com.leverx.blog.service.UserService;
import com.leverx.blog.service.converter.ArticleDtoConverter;
import com.leverx.blog.service.converter.UserDtoConverter;
import com.leverx.blog.service.pages.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ID = "User with such id not found";
    private static final String USER_WITH_SUCH_EMAIL_EXIST = "User with such email already exist";
    private static final String USER_NOT_FOUND_EMAIL = "User with such email not found";
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private ArticleDtoConverter articleDtoConverter;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoConverter userDtoConverter,
                           ArticleDtoConverter articleDtoConverter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.passwordEncoder = passwordEncoder;
        this.articleDtoConverter = articleDtoConverter;
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).stream().findAny()
                .ifPresent(user -> {
                    throw new NameAlreadyExistException(USER_WITH_SUCH_EMAIL_EXIST
                            + user.getEmail());
                });
        User user = userDtoConverter.unconvert(userDto);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Integer userID = userRepository.create(user);
        return findById(userID);
    }

    @Transactional
    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userDtoConverter::convert)
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND_ID + id));
    }

    @Transactional
    @Override
    public void remove(Integer id) {
        userRepository.delete(id);
    }

    @Override
    @Transactional
    public PageDto<ArticleDto> findUserArticles(String email, Integer page, Integer limit) {
        /*return userRepository.findForSingleResult(new UserByEmail(email))
                .map(user -> {
                    List<ArticleDto> articlesDto = userRepository.findUserArticles(email,
                            new PageImpl(page,limit))
                            .stream()
                            .map(articleDtoConverter::convert)
                            .collect(Collectors.toList());
                    Long countOfElements = userRepository.amountOfUserArticles(email);
                    //bad
                    Long allPages = countOfElements % limit == 0 ? countOfElements / limit : countOfElements / limit + 1;
                    return new PageDto<>(articlesDto, page, allPages,limit);
                })
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND_EMAIL + email));

         */
        return  null;
    }

    @Transactional
    @Override
    public PageDto<ArticleDto> findUserArticles(Integer id, Integer page, Integer limit) {
        return userRepository.findById(id)
                .map(user -> {
                    List<ArticleDto> articlesDto = userRepository.findUserArticles(id,
                            new PageImpl(page, limit))
                            .stream()
                            .map(articleDtoConverter::convert)
                            .collect(Collectors.toList());
                    Long countOfElements = userRepository.amountOfUserArticles(id);
                    Long allPages = countOfElements % limit == 0 ? countOfElements / limit : countOfElements / limit + 1;
                    return new PageDto<>(articlesDto, page, allPages, limit);
                })
                .orElseThrow(() -> new ObjectNotFoundException(USER_NOT_FOUND_ID + id));
    }
}

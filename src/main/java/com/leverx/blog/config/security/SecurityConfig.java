package com.leverx.blog.config.security;


import com.leverx.blog.model.User;
import com.leverx.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(getApplicationContext().getBean(UserDetailsService.class))
                .passwordEncoder(encoder());
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

    private static class UserService implements UserDetailsService {

        public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password.";
        private final UserRepository userRepository;

        @Autowired
        private UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Transactional(readOnly = true)
        @Override
        public UserDetails loadUserByUsername(String email) {
            return userRepository.findByEmail(email).stream()
                    .map(user -> new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            getAuthority(user)

                    ))
                    .findAny()
                    .orElseThrow(() -> new UsernameNotFoundException(INVALID_USERNAME_OR_PASSWORD));
        }

        private List<SimpleGrantedAuthority> getAuthority(User user) {
            return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        }
    }
}


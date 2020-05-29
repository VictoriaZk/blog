package com.leverx.blog.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;

    private String first_name;

    private String last_name;

    private String password;

    private String email;

    private Date created_ac;
}

package com.leverx.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created_ac;

}

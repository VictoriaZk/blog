package com.leverx.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH})
    private Integer author_id;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created_at;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date updated_at;

}

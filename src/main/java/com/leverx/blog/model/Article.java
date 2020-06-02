package com.leverx.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String status;

    @JoinColumn(name = "author_id")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private User user;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created_at;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date updated_at;

    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    @ManyToMany(targetEntity = Tag.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Tag> tagSet = new HashSet<>();

}

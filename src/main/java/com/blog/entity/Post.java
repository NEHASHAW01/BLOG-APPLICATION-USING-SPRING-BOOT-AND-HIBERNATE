package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts",uniqueConstraints=@UniqueConstraint(columnNames = {"title", "content"}))

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; // mapped to many.
    //when doing many to one mapping,one here is "Post" table, many here is "Comment" table.
    // when you do this type of mapping, many will be represented as List or Set. list can have duplicate value but set will not have duplicate.
    // cascade ALL  = whenever any changes is made in post(parent table), that changes should be reflected in comment(child table).
    // mappedBy = comments is mapped by post(object variable)
    //orphanRemoval = true - if post is removed, comment will become orphan, so, remove the orphan also

}

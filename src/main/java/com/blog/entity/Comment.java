package com.blog.entity;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY) // Comment is Many, and Post is one.
    @JoinColumn(name = "post_id", nullable = false) // post_id column automatically added in comment table.
    private Post post; // one is returned like this. not writing list as Many to mapped to one.
    //FetchTYpe - lazy and eager - there are two ways in which table is get loaded in memory.
    //Lazy - it will load only those table which are required when that module of project is running.( in above, only post and comment table is getting loaded).
    // eager - it will load all database table into the temporary memory(cache memory of hibernate) even without their requirement.
}

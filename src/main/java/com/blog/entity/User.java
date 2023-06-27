package com.blog.entity;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {  // 1st table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String password;
    private String username;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // cascade-if you make changes in one table then changes will be updated automatically in other tables.
    @JoinTable(name = "user_roles",  // create 3rd table - this will join User table(parent) with Role(child) table. // to join two table we use @JoinTable annotation.
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName
                    = "id"), // we are joining user_id column of user_roles table(3rd table) to id table of user table(parent).
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))  // we are joining role_id column of user_roles(3rd table) table to id table of Role table(child table).
    private Set<Role> roles;
}

//joinColumns - parent(user) to third table. // inverseJoinColumns is child to third table.

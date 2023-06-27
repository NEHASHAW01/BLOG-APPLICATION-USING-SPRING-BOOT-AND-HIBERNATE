package com.blog.repository;

import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { // entity class = Comment, wrapper class name = Long

    List<Comment> findByPostId(long id); // take the post id, find the record and returns the list of comment
//select * from Comment where post_id=1 (post_id in java will be written as PostId)
// it will return list of post for a given post_id.
// if you want to find by email use - list<Comment> findByEmail(String email)
// if you want to find by name use - list<Comment> findByName(String name)
// all above we have to build because there are no in-build method for that.


// get comment by commentId - in this case we don't have to build custom method in repository method.(findbyId is the built-in method available)
// get comment by emailId - in this case we have to build custom method in repository method.
}
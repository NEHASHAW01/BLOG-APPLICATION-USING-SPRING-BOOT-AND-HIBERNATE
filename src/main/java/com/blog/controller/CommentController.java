package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this will make it a API layer
@RequestMapping("/api/") // this will the common part in our url

public class CommentController {
    // construction based dependency injection
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // create comment
    //http://localhost:8080/api/posts/1/comments
    /* {
    "name" : "mike",
    "email" : "nehakkk@gmail.com",
    "body" : "nice post"
}*/
    @PostMapping("/posts/{postId}/comments") // post id(1) will be picked and stored in long postId.
    public ResponseEntity<CommentDto> createComment(@PathVariable(value =
            "postId") long postId, @RequestBody CommentDto commentDto) { // json object will be supplied in commentDto
        return new ResponseEntity<>(commentService.createComment(postId,
                commentDto), HttpStatus.CREATED);
    }

    //find comment for a given post id
    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments") // post id(1) will be picked and stored in long postId.
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) { // then it will initialise postId
        // then  getCommentsByPostId method will be called in impl.
        return commentService.getCommentsByPostId(postId); // controller take comment dto and display it on postman.
    }

    // get comment by commentId
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}") // url post id will go to {postId} and url comment id will go to {id}
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value =
            "postId") Long postId, @PathVariable(value = "id") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //Update comment by comment id
    //http://localhost:8080/api/posts/{postId}/comments{id}
    //first check the post exit or not, then check if the comment exit or not, then update the comment.
    // we also have to supply json object for comment updating -  @RequestBody CommentDto commentDto
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentdto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedCommentdto, HttpStatus.OK); // status code should be 200.
    }

    //Delete comment by comment id
    //http://localhost:8080/api/posts/{postId}/comments{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")     //it will also return a string message
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}


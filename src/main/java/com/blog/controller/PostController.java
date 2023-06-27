package com.blog.controller;
import java.util.List;
import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    // we are not using Autowired annotation, we are using constructor based injection.
    // click right, go to generate, click contructor
    public PostController(PostService postService){
        this.postService=postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')") //only admin can create post/ can access this method.the url can be accessed only by user.
    @PostMapping // help us to save data into database

    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){ //json object is copied in postDto with help of @RequestBody
        // return type is PostDto.
        // all @valid annotation will be applied to PostDto postDto. when data from json will be copied to postDto object, @valid annotation will
        // check whether the title is two chacter or not. after checking if any error happens, it should return an errpor message to postman.
        //BindingResult is the class which tell us whether validation error occured or not. In order to return back the error we need BindingResult class.
        //BindingResult class has hasErrors() method, if hasErrors() is true then it will return error message back to postman

        //if error will be there it will return this
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            // return type is generic(HttpStatus.INTERNAL_SERVER_ERROR) - So we have made ResponseEntity<PostDto> to ResponseEntity<>
        }

        //if error is not there it will be return this
        PostDto dto = postService.createPost(postDto); // controller give DTO object to service.
        return new ResponseEntity<>(dto, HttpStatus.CREATED); // HttpStatus.CREATED - it will display status code - 201. controller returned back dto object to postman.

    }
    //http://localhost:8080/api/posts/get
    @GetMapping("/get") // help us to read data from database
    public List<PostDto>listAllPosts() { //return type is Postdto
        List<PostDto> postDtos = postService.listAllPosts();
        return postDtos;
    }
    // get post by id
    //http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK); // Status code 200//
    }

    //update post by id rest api
    // http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')") //only admin can update post/ can access this method.the url can be accessed only by user.
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id) {
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK); // Status code 200
    }

    //delete post by id
    // http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')") //only admin can delete post/ can access this method.the url can be accessed only by user.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is Deleted", HttpStatus.OK); // Status code 200

        }

    //pagination
    // http://localhost:8080/api/posts?pageNo=0&pageSize=5
    // http://localhost:8080/api/posts?pageNo=0
    // http://localhost:8080/api/posts?pageSize=5
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
    @GetMapping
    public List<PostDto> listAllPostsPage(
            @RequestParam(value="pageNo" , defaultValue="0" , required = false) int pageNo, //RequestParam will fetch value(pageNo and pageSize) from url and initilize pageNo and pageSize.
            @RequestParam(value="pageSize" , defaultValue="0" , required = false) int pageSize,
            @RequestParam(value="sortBy" , defaultValue="id" , required = false) String sortBy,
            @RequestParam(value="sortDir" , defaultValue="asc" , required = false) String sortDir){
        List<PostDto> postDtos = postService.listAllPostsPage(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }
}



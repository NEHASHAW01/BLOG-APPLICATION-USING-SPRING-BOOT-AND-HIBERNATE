package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    //service layer only return dto.
    PostDto createPost(PostDto postDto);

    List<PostDto> listAllPosts();

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePostById(long id);

    List<PostDto> listAllPostsPage(int pageNo, int pageSize, String sortBy, String sortDir);
}

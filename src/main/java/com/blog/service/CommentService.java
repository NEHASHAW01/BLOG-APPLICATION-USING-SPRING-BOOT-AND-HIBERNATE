package com.blog.service;
import com.blog.payload.CommentDto;

import java.util.List;
public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto); // taking dto and saving it and returing back dto.
    //postId - whatever comment is saved, its again a post, to locate a post we require post id. we need post id to save the comment.

    List<CommentDto> getCommentsByPostId(long postId); // get comment by post id

    CommentDto getCommentById(Long postId, Long commentId); // get comment based on comment id


    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto); //Update comment by comment id

    void deleteComment(Long postId, Long commentId);
}

// service layer always return dto back.
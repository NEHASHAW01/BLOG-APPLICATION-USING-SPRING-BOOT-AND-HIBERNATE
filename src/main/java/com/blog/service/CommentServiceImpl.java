package com.blog.service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import org.springframework.stereotype.Service;
import com.blog.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository; // we require postRepository as it will take post id number and check if the post exist or not. and for that post we will save the comments

    public CommentServiceImpl(CommentRepository commentRepository, // construction based injection.
                              PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        // retrieve post entity by id - check if post exist or not.// return post object.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id "+postId));

        Comment comment = mapToEntity(commentDto); // convert commentDto to comment entity object.
        //set post to comment entity
        comment.setPost(post); // comment need to be saved in a post.whatever comment is there in "comment" is saved in this post.
        Comment newComment = commentRepository.save(comment); // it will save the comment.

        CommentDto dto = mapToDTO(newComment); //convert newComment back to dto object.
        return dto; // return dto object to controller layer.
    }

    // get comment by post id
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) { // postId will take the id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id "+postId));
        // first check if the post exist or not then find comments for that post id.
        // find comment based on post id.
        List<Comment> comments = commentRepository.findByPostId(postId); // find the post and it return list of comment for that post.
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList()); // convert comment to dto and passed it comment controller..
    }


    // get comment based on comment id
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id "+postId));
        // first check if the post exist or not then find comment by comment id.
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" +commentId));
        // above
        return mapToDTO(comment);
    }

    //Update comment by comment id
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id "+postId)); // first check if the post exist or not
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment not found with id "+commentId)); // then check if the comment exist or not

        comment.setName(commentDto.getName()); // get name from dto object then set the name to comment entity.
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment); // once saved we will get update comment then we convert it to dto object
        return mapToDTO(updatedComment); // pass the updatecomment dto object to controller layer.
    }

    //Delete comment by comment id
    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id "+postId)); // first check if the post exist or not
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment not found with id "+commentId)); // then check if the comment exist or not

        commentRepository.deleteById(commentId); // just deleted record no need to return anything.

    }


    //convert comment Entity to Comment Dto
    private CommentDto mapToDTO(Comment comment){ // take comment object and convert it to dto
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    //convert CommentDto to Comment Entity
    private Comment mapToEntity(CommentDto commentDto){ // take dto object and convert it to comment object.
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}

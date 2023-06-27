package com.blog.service;
import com.blog.exception.ResourceNotFoundException;
import com.blog.entity.Post;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
    public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper; // in order initialise modelMapper we will do construction based injection

        public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper ) { // construction based injection
            this.postRepository = postRepository;
            this.modelMapper = modelMapper; // when it suppose to create object , spring will refer to configuration file.
            // object will be created by @bean annotation and that object reference get injected in modelMapper objecct.
        }

        @Override
        public PostDto createPost(PostDto postDto) { //To PostDto method, postDto object is supplied.
            // convert DTO to entity as we can't save dto directly to database.
            Post post = mapToEntity(postDto);
            Post newPost = postRepository.save(post); // SAVING THE ENTITY. after saving it will return back the content what it has saved.
            // we will put that returned content in new reference variable of entity - newPost.
            // convert entity to DTO as returned type of createPost method is PostDto.
            PostDto postResponse = mapToDTO(newPost); // returned back content is in form of entity, controlled must convert it to dto, return dto back to controller.
            return postResponse; // dto response passed to postman.
        }

    @Override
    public List<PostDto> listAllPosts() { // service layer return dto and take entity and convert it to dto
        List<Post> posts = postRepository.findAll(); // repository layer give list of entity which need to be converted into dto object by service layer.
        List<PostDto> postDtos = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
            Post post = postRepository.findById(id).orElseThrow( //check if the post exist or not. if id is found it will take into post entity object else throw exception.
                    ()-> new ResourceNotFoundException("Post not found with id: "+id));
            //lambda expression usage - write code in one line, if u want to throw custom exception use lambda expression.
            // this part will create exception object, exception object will call the constructor present in ResourceNotFoundException class.
        //if record is found we need it to convert into dto
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow( //take id, find whether post exist or not.
                ()-> new ResourceNotFoundException("Post not found with id: "+id));

        Post newPost = mapToEntity(postDto); // in order to get data from database, dto object need to be converted to entity.
        newPost.setId(id); //in that entity we will give id number to get required post.

        Post updatedPost = postRepository.save(newPost); //save the updated post
        PostDto dto = mapToDTO(updatedPost); //convert the undated post to dto
        return dto;

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow( //take id, find whether post exist or not.
                ()-> new ResourceNotFoundException("Post not found with id: "+id));
        postRepository.deleteById(id);


    }
    //pagination
    @Override
    public List<PostDto> listAllPostsPage(int pageNo, int pageSize, String sortBy, String sortDir) {
         Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
         Sort.by(sortBy).descending();
         //above code(ternary operators) - Sort.Direction.ASC.name() = this always return asc. we are comparing entered sortDir value with that.
        //if true, after question mark which ever first statement is there will get executed, if false then 2nd statement will be executed.
        //Sort sort = Sort.by(sortBy);  // convert sortBy string  into sort object(returned object).(sort.by - method for conversion)
            //of(parameters) - overloaded "of" methods present in PageRequest class
        // service layer should fetch the record based on pagesize & pageno.
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort); // insert in pageable object.
        Page<Post> listOfPosts = postRepository.findAll(pageable); // it will not give list of post.Its return type is page.
        //pageable will extract that much data depending on pagesize and no.

        //convert page into list.
        List<Post> posts = listOfPosts.getContent(); //get content for page object.

        List<PostDto> postDtos = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        return postDtos;
    }

    // convert Entity into DTO
        /*PostDto mapToDTO(Post post) {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setDescription(post.getDescription());
            postDto.setContent(post.getContent());
            return postDto;
        } alternate of doing mapping using model mapper*/
    // boilerplate got removed.

        PostDto mapToDTO(Post post) {
        PostDto dto = modelMapper.map(post,PostDto.class); // take content from post and put that into PostDto
        return dto;}

        // convert DTO to entity
        /*Post mapToEntity(PostDto postDto) {
            Post post = new Post();
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            return post;

         */
            Post mapToEntity(PostDto postDto) {
            Post post = modelMapper.map(postDto,Post.class); // take content from postDto and convert it to Post.class
                return post; // post is local variable.

        }
    }
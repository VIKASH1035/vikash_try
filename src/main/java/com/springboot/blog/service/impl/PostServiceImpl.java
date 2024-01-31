package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // convert DTO to entity.
        Post post = mapToEntity(postDTO);
        // save object to db.
        Post newPost = postRepository.save(post);
        // convert entity to dto
        return mapToDto(newPost);
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
       return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
      return mapToDto(post);
    }

    @Override
    public PostDTO updatePostById(PostDTO postDTO, long id) {
        // get post by id from database.
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        Post updatedPost = postRepository.save(post);
        return  mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        postRepository.delete(post);

    }

    // converted  entity into dto
    private  PostDTO mapToDto(Post post){
         PostDTO postDTO = new PostDTO();
         postDTO.setId(post.getId());
         postDTO.setContent(post.getContent());
         postDTO.setTitle(post.getTitle());
         postDTO.setDescription(post.getDescription());
         return  postDTO;
    }

    //CONVERT dto to entity.
    private  Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return  post;
    }

}

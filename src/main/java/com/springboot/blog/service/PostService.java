package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPost();

    PostDTO getPostById(long id);

    PostDTO updatePostById(PostDTO postDTO , long id);

    void deletePostById(long id);


}

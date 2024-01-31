package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    @Autowired
    PostService postService;


    // create a blog post.
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO ){
      return  new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //get all the post
    @GetMapping
    public ResponseEntity<List<PostDTO>>getAllPosts(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }

    //get post by id
   @GetMapping("/{id}")
    public ResponseEntity<PostDTO>getPostById(@PathVariable(name = "id")  long id)
   {
    return  new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
   }

   // update post by id.
   @PutMapping("/{id}")
   public ResponseEntity<PostDTO>updatePostById(@RequestBody PostDTO postDTO , @PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePostById(postDTO,id),HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public  ResponseEntity<String>deletePostById(@PathVariable(name = "id") long id ){
         postService.deletePostById(id);
        return  ResponseEntity.ok("Bro you just delete the post! ");
   }


}

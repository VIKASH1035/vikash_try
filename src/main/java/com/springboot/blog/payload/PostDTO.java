package com.springboot.blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class PostDTO {
    private long id;
    private  String title;
    private  String description;
    private String content;

}

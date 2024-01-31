package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "image")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    // Getter and Setter methods
}

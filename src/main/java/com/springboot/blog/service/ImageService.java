package com.springboot.blog.service;

import com.springboot.blog.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    public Image saveImage(byte[] imageData);

    public Optional<Image> getImageById(Long id);
     public List<Image> getAllImages();
}

package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Image;
import com.springboot.blog.repository.ImageRepository;
import com.springboot.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public Image saveImage(byte[] imageData) {
        Image image = new Image();
        image.setData(imageData);
        imageRepository.save(image);
        return image;
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}

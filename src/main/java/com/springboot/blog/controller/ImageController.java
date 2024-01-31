package com.springboot.blog.controller;

import com.springboot.blog.entity.Image;
import com.springboot.blog.payload.ImageResponseDTO;
import com.springboot.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;


    // upload a single image.
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody byte[] imageData) {
        imageService.saveImage(imageData);
        return ResponseEntity.ok("Image uploaded successfully!");
    }

    // upload multiple images in a single row.

    @PostMapping("/uploadall")
    public  ResponseEntity<String> uploadAll(@RequestParam("files") List<MultipartFile> files ){
        List<Image> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // Create a new Image entity
                Image image = new Image();
                image.setData(file.getBytes());

                // Save the image and add it to the list
                savedImages.add(imageService.saveImage(image.getData()));
            } catch (IOException e) {
                // Handle exception if needed
                System.out.println(e.getMessage());
            }
        }
        return  ResponseEntity.ok("all images are uploaded baby!. ");
    }

    // get all image data in json format.
    @GetMapping("all")
    public ResponseEntity<List<ImageResponseDTO>> getAllImages() {
        List<Image> images = imageService.getAllImages();

        if (!images.isEmpty()) {
            List<ImageResponseDTO> imageResponses = images.stream()
                    .map(this::mapToImageResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(imageResponses);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private ImageResponseDTO mapToImageResponse(Image image) {
        return new ImageResponseDTO(image.getId(), Base64.getEncoder().encodeToString(image.getData()));
    }





    @GetMapping("/image/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Long id) {
        // Fetch image data based on the ID
        Optional<Image> optionalImage = imageService.getImageById(id);

        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.getData().length);
            headers.setContentDispositionFormData("inline", "image.jpg");
            ByteArrayResource resource = new ByteArrayResource(image.getData());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.mangalist.manga.service;

import com.mangalist.manga.config.CloudinaryConfig;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    private final CloudinaryConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public ImageUploadService(CloudinaryConfig config) {
        this.config = config;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String cloudinaryUrl = "https://api.cloudinary.com/v1_1/" + config.cloudName + "/image/upload";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        body.add("upload_preset", config.uploadPreset); // Unsigned upload

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(cloudinaryUrl, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().get("secure_url").toString();
        } else {
            throw new RuntimeException("Image upload failed: " + response.getStatusCode());
        }
    }
}

package com.mangalist.manga.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    public String cloudName;

    @Value("${cloudinary.api-key}")
    public String apiKey;

    @Value("${cloudinary.api-secret}")
    public String apiSecret;

    @Value("${cloudinary.upload-preset}")
    public String uploadPreset;
}

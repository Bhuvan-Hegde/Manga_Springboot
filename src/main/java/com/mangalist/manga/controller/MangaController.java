package com.mangalist.manga.controller;

import com.mangalist.manga.dto.MangaRequest;
import com.mangalist.manga.model.Manga;
import com.mangalist.manga.model.MangaStatus;
import com.mangalist.manga.service.ImageUploadService;
import com.mangalist.manga.service.MangaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    private final MangaService mangaService;
    private final ImageUploadService imageUploadService;

    public MangaController(MangaService mangaService, ImageUploadService imageUploadService) {
        this.mangaService = mangaService;
        this.imageUploadService = imageUploadService;
    }

    @GetMapping
    public List<Manga> getAllManga() {
        return mangaService.getAllManga();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Manga addManga(
            @RequestPart("manga") MangaRequest request,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        Manga manga = new Manga();
        manga.setName(request.getName());
        manga.setTotalChapters(request.getTotalChapters());
        manga.setCompletedChapters(request.getCompletedChapters());
        manga.setStatus(request.getStatus());
        manga.setReleaseStatus(request.getReleaseStatus());
        manga.setComment(request.getComment());

        if (request.getCompletedChapters() != null && request.getTotalChapters() != null &&
                request.getCompletedChapters().equals(request.getTotalChapters())) {
            manga.setStatus(MangaStatus.Completed);
        }

        if (coverImage != null && !coverImage.isEmpty()) {
            String imageUrl = imageUploadService.uploadImage(coverImage);
            manga.setCoverImage(imageUrl);
        }

        return mangaService.addManga(manga, request.getUserId());
    }

    @GetMapping("/{id}")
    public Manga getMangaById(@PathVariable Long id) {
        return mangaService.getMangaById(id)
                .orElseThrow(() -> new RuntimeException("Manga Not Found"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Manga updateManga(
            @PathVariable Long id,
            @RequestPart("manga") Manga updatedManga,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        if (coverImage != null && !coverImage.isEmpty()) {
            String imageUrl = imageUploadService.uploadImage(coverImage);
            updatedManga.setCoverImage(imageUrl);
        }

        return mangaService.updateManga(id, updatedManga);
    }

    @DeleteMapping("/{id}")
    public void deleteManga(@PathVariable Long id) {
        mangaService.deleteManga(id);
    }
}

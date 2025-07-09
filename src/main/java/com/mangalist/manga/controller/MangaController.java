package com.mangalist.manga.controller;

import com.mangalist.manga.dto.MangaRequest;
import com.mangalist.manga.model.Manga;
import com.mangalist.manga.model.MangaStatus;
import com.mangalist.manga.service.MangaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping
    public List<Manga> getAllManga() {
        return mangaService.getAllManga();
    }

    @PostMapping
    public Manga addManga(@RequestBody MangaRequest request) {
        Manga manga = new Manga();
        manga.setName(request.getName());
        manga.setTotalChapters(request.getTotalChapters());
        manga.setCompletedChapters(request.getCompletedChapters());
        manga.setStatus(request.getStatus());
        manga.setReleaseStatus(request.getReleaseStatus());
        manga.setComment(request.getComment());
        manga.setCoverImage(request.getCoverImage());
        System.out.println("Received cover image URL: " + request.getCoverImage());



        if (manga.getCompletedChapters() != null && manga.getTotalChapters() != null &&
                manga.getCompletedChapters().equals(manga.getTotalChapters())) {
            manga.setStatus(MangaStatus.Completed);
        }


        return mangaService.addManga(manga, request.getUserId());
    }

    @GetMapping("/{id}")
    public Manga getMangaById(@PathVariable Long id){
        return mangaService.getMangaById(id).orElseThrow(() -> new RuntimeException("Manga Not Found"));
    }

    @PutMapping("/{id}")
    public Manga updateManga(@PathVariable Long id, @RequestBody MangaRequest request) {
        Manga manga = new Manga();
        manga.setName(request.getName());
        manga.setTotalChapters(request.getTotalChapters());
        manga.setCompletedChapters(request.getCompletedChapters());
        manga.setStatus(request.getStatus());
        manga.setReleaseStatus(request.getReleaseStatus());
        manga.setComment(request.getComment());
        manga.setCoverImage(request.getCoverImage());

        if (manga.getCompletedChapters() != null && manga.getTotalChapters() != null &&
                manga.getCompletedChapters().equals(manga.getTotalChapters())) {
            manga.setStatus(MangaStatus.Completed);
        }

        return mangaService.updateManga(id, manga);
    }


    @DeleteMapping("/{id}")
    public void deleteManga(@PathVariable Long id) {
        mangaService.deleteManga(id);
    }
}

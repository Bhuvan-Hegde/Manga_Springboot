package com.mangalist.manga.service;

import com.mangalist.manga.model.Manga;
import com.mangalist.manga.model.User;
import com.mangalist.manga.repository.MangaRepository;
import com.mangalist.manga.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;
    private final UserRepository userRepository;


    public MangaService(MangaRepository mangaRepository, UserRepository userRepository, ImageUploadService imageUploadService) {
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
    }

    public List<Manga> getAllManga() {
        return mangaRepository.findAll();
    }

    public Manga addManga(Manga manga, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        manga.setUser(user);
        return mangaRepository.save(manga);
    }

    public Optional<Manga> getMangaById(Long id) {
        return mangaRepository.findById(id);
    }

    // 🔁 Updated: Support image update
    public Manga updateManga(Long id, Manga updatedManga) {
        return mangaRepository.findById(id).map(manga -> {
            manga.setName(updatedManga.getName());
            manga.setTotalChapters(updatedManga.getTotalChapters());
            manga.setCompletedChapters(updatedManga.getCompletedChapters());
            manga.setStatus(updatedManga.getStatus());
            manga.setReleaseStatus(updatedManga.getReleaseStatus());
            manga.setComment(updatedManga.getComment());

            return mangaRepository.save(manga);
        }).orElseThrow(() -> new RuntimeException("Manga not found with id " + id));
    }

    public void deleteManga(Long id) {
        mangaRepository.deleteById(id);
    }
}

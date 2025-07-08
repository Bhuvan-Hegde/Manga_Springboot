package com.mangalist.manga.dto;

import com.mangalist.manga.model.MangaStatus;
import com.mangalist.manga.model.ProgressStatus;
import lombok.Data;

@Data
public class MangaRequest {
    private String name;
    private Integer totalChapters;
    private Integer completedChapters;
    private String comment;
    private MangaStatus status;
    private ProgressStatus releaseStatus;
    private Long userId;

    // Getters and Setters
}

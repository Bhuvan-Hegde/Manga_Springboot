package com.mangalist.manga.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Manga {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer totalChapters;
    private Integer completedChapters;
    private String comment;

    @Column(nullable = true)
    private String coverImage;

    @Enumerated(EnumType.STRING)
    private MangaStatus status;

    @Enumerated(EnumType.STRING)
    private ProgressStatus releaseStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}

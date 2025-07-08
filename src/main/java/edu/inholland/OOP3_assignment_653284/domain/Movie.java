package edu.inholland.OOP3_assignment_653284.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer year;
    private String director;
    private String genre;

    @ElementCollection
    private List<String> similarTitles = new ArrayList<>();

    @ElementCollection
    private List<String> imagePaths = new ArrayList<>();

    private Boolean watched = false;
    private Integer rating;       // 1–5; null = not rated

    /* Lombok can generate the boilerplate if you like:
       @Getter @Setter @NoArgsConstructor @AllArgsConstructor
       but manual getters/setters work fine too. */
}

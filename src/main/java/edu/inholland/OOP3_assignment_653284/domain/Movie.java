package edu.inholland.OOP3_assignment_653284.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "movie_year")         // <-- maps to MOVIE_YEAR
    private Integer releaseYear;

    private String director;
    private String genre;

    /* ----- Collections ----- */
    @ElementCollection
    private List<String> similarTitles = new ArrayList<>();

    @ElementCollection
    private List<String> imagePaths = new ArrayList<>();

    private Boolean watched = false;
    private Integer rating;              // 1–5; null = not rated

    /* ===== Setters ===== */
    public void setTitle(String title)               { this.title = title; }
    public void setReleaseYear(Integer releaseYear)  { this.releaseYear = releaseYear; }
    public void setDirector(String director)         { this.director = director; }
    public void setGenre(String genre)               { this.genre = genre; }
    public void setSimilarTitles(List<String> list)  { this.similarTitles = list; }
    public void setImagePaths(List<String> list)     { this.imagePaths = list; }
    public void setWatched(Boolean watched)          { this.watched = watched; }
    public void setRating(Integer rating)            { this.rating = rating; }

    /* ===== Getters ===== */
    public Long getId()                  { return id; }
    public String getTitle()             { return title; }
    public Integer getReleaseYear()      { return releaseYear; }
    public String getDirector()          { return director; }
    public String getGenre()             { return genre; }
    public List<String> getSimilarTitles(){ return similarTitles; }
    public List<String> getImagePaths()  { return imagePaths; }
    public Boolean getWatched()          { return watched; }
    public Integer getRating()           { return rating; }
}

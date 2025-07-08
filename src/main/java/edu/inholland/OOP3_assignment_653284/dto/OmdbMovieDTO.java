package edu.inholland.OOP3_assignment_653284.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OmdbMovieDTO(
        @JsonProperty("Title")    String title,
        @JsonProperty("Year")     String year,
        @JsonProperty("Director") String director,
        @JsonProperty("Genre")    String genre,
        @JsonProperty("imdbID")   String imdbId
) {}

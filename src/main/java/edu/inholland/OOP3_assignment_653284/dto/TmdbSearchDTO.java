package edu.inholland.OOP3_assignment_653284.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbSearchDTO(
        @JsonProperty("results") List<Result> results
) {
    public record Result(
            @JsonProperty("title") String title,
            @JsonProperty("id") int id
    ) {}
}

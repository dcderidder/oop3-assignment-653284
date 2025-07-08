package edu.inholland.OOP3_assignment_653284.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Maps GET /movie/{id}/images response */
public record TmdbImagesDTO(
        @JsonProperty("backdrops") List<FilePath> backdrops,

        /* >>> add this field so imgDTO.posters() is available <<< */
        @JsonProperty("posters")   List<FilePath> posters
) {
    public record FilePath(
            @JsonProperty("file_path") String filePath
    ) {}
}

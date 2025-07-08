package edu.inholland.OOP3_assignment_653284.client;

import edu.inholland.OOP3_assignment_653284.dto.TmdbImagesDTO;
import edu.inholland.OOP3_assignment_653284.dto.TmdbSearchDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TmdbClient {

    private final WebClient wc;
    private final String apiKey;

    public TmdbClient(@Value("${tmdb.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.wc = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3")
                .build();
    }

    public Mono<TmdbSearchDTO> search(String title) {
        return wc.get()
                .uri(uri -> uri.path("/search/movie")
                        .queryParam("query", title)
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(TmdbSearchDTO.class);
    }

    public Mono<TmdbImagesDTO> images(int movieId) {
        return wc.get()
                .uri(uri -> uri.path("/movie/{id}/images")
                        .queryParam("api_key", apiKey)
                        .build(movieId))
                .retrieve()
                .bodyToMono(TmdbImagesDTO.class);
    }
}

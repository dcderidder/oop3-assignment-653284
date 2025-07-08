package edu.inholland.OOP3_assignment_653284.client;

import edu.inholland.OOP3_assignment_653284.dto.OmdbMovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OmdbClient {

    private final WebClient wc;
    private final String apiKey;

    public OmdbClient(@Value("${omdb.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.wc = WebClient.builder()
                .baseUrl("https://www.omdbapi.com")
                .build();
    }

    public Mono<OmdbMovieDTO> fetchByTitle(String title) {
        return wc.get()
                .uri(uri -> uri.queryParam("t", title)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieDTO.class);
    }
}

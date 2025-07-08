package edu.inholland.OOP3_assignment_653284.service;

import edu.inholland.OOP3_assignment_653284.client.OmdbClient;
import edu.inholland.OOP3_assignment_653284.client.TmdbClient;
import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.domain.MovieRepository;
import edu.inholland.OOP3_assignment_653284.dto.*;
import edu.inholland.OOP3_assignment_653284.util.ImageDownloader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieEnrichmentService {

    private final MovieRepository repo;
    private final OmdbClient omdb;
    private final TmdbClient tmdb;
    private final ImageDownloader downloader;

    public MovieEnrichmentService(
            MovieRepository repo,
            OmdbClient omdb,
            TmdbClient tmdb,
            ImageDownloader downloader) {
        this.repo = repo;
        this.omdb = omdb;
        this.tmdb = tmdb;
        this.downloader = downloader;
    }

    /* ---------- CREATE with real enrichment ---------- */
    public Movie addByTitle(String title) {

        Mono<OmdbMovieDTO> omMono = omdb.fetchByTitle(title);
        Mono<TmdbSearchDTO> tmdbSearchMono = tmdb.search(title);

        OmdbMovieDTO omdbData = omMono.block();
        TmdbSearchDTO search = tmdbSearchMono.block();

        if (omdbData == null || search.results().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Movie not found in OMDb or TMDB");
        }

        int tmdbId = search.results().get(0).id();
        TmdbImagesDTO imgDTO = tmdb.images(tmdbId).block();

        // collect first few paths from backdrops + posters
        List<String> relPaths = new ArrayList<>();
        imgDTO.backdrops().stream().limit(5).forEach(f -> relPaths.add(f.filePath()));
        imgDTO.posters().stream().limit(5).forEach(f -> relPaths.add(f.filePath()));

        List<String> saved;
        try {
            saved = downloader.download(relPaths);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Image download failed", e);
        }

        Movie m = new Movie();
        m.setTitle(omdbData.title());
        m.setReleaseYear(Integer.valueOf(omdbData.year()));
        m.setDirector(omdbData.director());
        m.setGenre(omdbData.genre());
        m.setImagePaths(saved);

        return repo.save(m);
    }

    /* ---------- READ ---------- */
    public Page<Movie> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /* ---------- UPDATE / DELETE (same as before) ---------- */
    public void setWatched(Long id, Boolean watched) { /* ... */ }
    public void setRating(Long id, Integer rating)   { /* ... */ }
    public void delete(Long id)                      { /* ... */ }
}

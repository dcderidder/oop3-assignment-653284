package edu.inholland.OOP3_assignment_653284.service;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.domain.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Business-logic layer for Movie operations.
 * — addByTitle(...) is still a stub (will later call OMDb + TMDB).
 * — list(...) returns a paginated Page<Movie> for GET /movies.
 */
@Service
public class MovieEnrichmentService {

    private final MovieRepository repo;

    /* ---- constructor injection ---- */
    public MovieEnrichmentService(MovieRepository repo) {
        this.repo = repo;
    }

    /* ---------- CREATE ---------- */

    /**
     * Temporary implementation that fabricates minimal data,
     * then persists the Movie so we can test the pipeline.
     */
    public Movie addByTitle(String title) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setReleaseYear(2000);
        m.setDirector("N/A");
        m.setGenre("N/A");
        return repo.save(m);
    }

    /**
     * Returns a Page of movies based on the given Pageable
     * (page number, size, sort order).
     */
    public Page<Movie> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}

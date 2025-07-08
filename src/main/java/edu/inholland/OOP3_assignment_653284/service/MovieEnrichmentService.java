package edu.inholland.OOP3_assignment_653284.service;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.domain.MovieRepository;
import org.springframework.stereotype.Service;

/**
 * Business-logic layer.
 * For now, it just creates a stub Movie record so
 * you can verify DB writes and the controller path.
 */
@Service
public class MovieEnrichmentService {

    private final MovieRepository repo;

    // ---- constructor injection ----
    public MovieEnrichmentService(MovieRepository repo) {
        this.repo = repo;
    }

    /**
     * Stub implementation – later you’ll call OMDb/TMDB here.
     */
    public Movie addByTitle(String title) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setReleaseYear(2000);
        m.setDirector("N/A");
        m.setGenre("N/A");
        return repo.save(m);
    }
}



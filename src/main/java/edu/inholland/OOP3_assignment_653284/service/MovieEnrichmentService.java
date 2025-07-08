package edu.inholland.OOP3_assignment_653284.service;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.domain.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MovieEnrichmentService {

    private final MovieRepository repo;

    public MovieEnrichmentService(MovieRepository repo) {
        this.repo = repo;
    }

    /* ---------- CREATE (stub) ---------- */
    public Movie addByTitle(String title) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setReleaseYear(2000);
        m.setDirector("N/A");
        m.setGenre("N/A");
        return repo.save(m);
    }

    /* ---------- READ (paginated) ---------- */
    public Page<Movie> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /* ---------- UPDATE watched flag ---------- */
    public void setWatched(Long id, Boolean watched) {
        Movie m = repo.findById(id)
                .orElseThrow(() -> notFound(id));
        m.setWatched(watched);
        repo.save(m);
    }

    /* ---------- UPDATE rating ---------- */
    public void setRating(Long id, Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Rating must be 1-5");
        }
        Movie m = repo.findById(id)
                .orElseThrow(() -> notFound(id));
        m.setRating(rating);
        repo.save(m);
    }

    /* ---------- DELETE ---------- */
    public void delete(Long id) {
        if (!repo.existsById(id)) throw notFound(id);
        repo.deleteById(id);
    }

    /* ---------- helper ---------- */
    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Movie " + id + " not found");
    }
}

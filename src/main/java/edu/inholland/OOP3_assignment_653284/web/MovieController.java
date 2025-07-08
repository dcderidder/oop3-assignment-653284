package edu.inholland.OOP3_assignment_653284.web;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.service.MovieEnrichmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST layer – exposes CRUD endpoints for the watch-list.
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieEnrichmentService svc;

    public MovieController(MovieEnrichmentService svc) {
        this.svc = svc;
    }

    /* ---------- CREATE ---------- */
    @PostMapping
    public Movie add(@RequestParam String title) {
        return svc.addByTitle(title);
    }

    /* ---------- READ (paginated) ---------- */
    @GetMapping
    public Page<Movie> list(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return svc.list(pageable);
    }

    /* ---------- UPDATE watched flag ---------- */
    @PatchMapping("/{id}/watched")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setWatched(@PathVariable Long id,
                           @RequestParam Boolean watched) {
        svc.setWatched(id, watched);
    }

    /* ---------- UPDATE rating ---------- */
    @PatchMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRating(@PathVariable Long id,
                          @RequestParam Integer rating) {
        svc.setRating(id, rating);
    }

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}

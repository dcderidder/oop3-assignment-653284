package edu.inholland.OOP3_assignment_653284.web;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.service.MovieEnrichmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * REST layer for Movie Watchlist.
 * Exposes:
 *   • POST /movies            – add a new movie by title (stub data for now)
 *   • GET  /movies            – paginated list
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieEnrichmentService svc;

    /* ---- constructor injection ---- */
    public MovieController(MovieEnrichmentService svc) {
        this.svc = svc;
    }

    /* ---------- POST /movies ---------- */
    /**
     * Example:  POST /movies?title=Jaws
     * Returns the newly created Movie entity (JSON).
     */
    @PostMapping
    public Movie add(@RequestParam String title) {
        return svc.addByTitle(title);
    }

    /* ---------- GET /movies ---------- */
    /**
     * Example: GET /movies?page=0&size=10&sort=title,asc
     * Spring automatically binds page/size/sort from query params.
     */
    @GetMapping
    public Page<Movie> list(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return svc.list(pageable);
    }
}


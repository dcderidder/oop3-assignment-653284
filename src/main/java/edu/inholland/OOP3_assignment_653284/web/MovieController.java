package edu.inholland.OOP3_assignment_653284.web;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.service.MovieEnrichmentService;
import org.springframework.web.bind.annotation.*;

/**
 * REST layer – exposes /movies endpoints.
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieEnrichmentService svc;

    // ---- constructor injection ----
    public MovieController(MovieEnrichmentService svc) {
        this.svc = svc;
    }

    /**
     * Example:  POST /movies?title=Jaws
     */
    @PostMapping
    public Movie add(@RequestParam String title) {
        return svc.addByTitle(title);
    }
}


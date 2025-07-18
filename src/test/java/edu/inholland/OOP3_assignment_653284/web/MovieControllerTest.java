package edu.inholland.OOP3_assignment_653284.web;

import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.service.MovieEnrichmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean MovieEnrichmentService service;

    @Test
    void add_shouldReturnMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        Mockito.when(service.addByTitle("The Matrix")).thenReturn(movie);

        mockMvc.perform(post("/movies")
                        .param("title", "The Matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Matrix")));
    }

    @Test
    void list_shouldReturnPagedMovies() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        Mockito.when(service.list(any())).thenReturn(new PageImpl<>(List.of(movie), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("The Matrix")));
    }
}

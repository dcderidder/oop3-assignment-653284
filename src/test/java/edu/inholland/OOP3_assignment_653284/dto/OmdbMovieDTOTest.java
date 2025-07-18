package edu.inholland.OOP3_assignment_653284.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OmdbMovieDTOTest {
    @Test
    void testFields() {
        OmdbMovieDTO dto = new OmdbMovieDTO("t", "y", "d", "g", "id");
        assertEquals("t", dto.title());
        assertEquals("y", dto.year());
    }
}

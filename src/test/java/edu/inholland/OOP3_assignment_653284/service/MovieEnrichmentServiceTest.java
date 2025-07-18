package edu.inholland.OOP3_assignment_653284.service;

import edu.inholland.OOP3_assignment_653284.client.OmdbClient;
import edu.inholland.OOP3_assignment_653284.client.TmdbClient;
import edu.inholland.OOP3_assignment_653284.domain.Movie;
import edu.inholland.OOP3_assignment_653284.domain.MovieRepository;
import edu.inholland.OOP3_assignment_653284.dto.*;
import edu.inholland.OOP3_assignment_653284.util.ImageDownloader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieEnrichmentServiceTest {

    @Mock private MovieRepository repo;
    @Mock private OmdbClient omdb;
    @Mock private TmdbClient tmdb;
    @Mock private ImageDownloader downloader;
    @InjectMocks private MovieEnrichmentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new MovieEnrichmentService(repo, omdb, tmdb, downloader);
    }

    @Test
    void addByTitle_shouldReturnMovie_whenAllApisReturnValidData() throws Exception {
        String title = "The Matrix";
        OmdbMovieDTO omdbMovieDTO = new OmdbMovieDTO(title, "1999", "Wachowski", "Action", "id123");
        TmdbSearchDTO.Result tmdbResult = new TmdbSearchDTO.Result(title, 10);
        TmdbSearchDTO tmdbSearchDTO = new TmdbSearchDTO(List.of(tmdbResult));
        TmdbImagesDTO.FilePath fp1 = new TmdbImagesDTO.FilePath("/poster1.jpg");
        TmdbImagesDTO imgDTO = new TmdbImagesDTO(List.of(fp1), List.of(fp1));
        List<String> downloaded = List.of("images/poster1.jpg");

        when(omdb.fetchByTitle(title)).thenReturn(Mono.just(omdbMovieDTO));
        when(tmdb.search(title)).thenReturn(Mono.just(tmdbSearchDTO));
        when(tmdb.images(10)).thenReturn(Mono.just(imgDTO));
        when(downloader.download(anyList())).thenReturn(downloaded);
        when(repo.save(any(Movie.class))).thenAnswer(inv -> inv.getArgument(0));

        Movie m = service.addByTitle(title);

        assertNotNull(m);
        assertEquals(title, m.getTitle());
        assertEquals(1999, m.getReleaseYear());
        assertEquals("Wachowski", m.getDirector());
        assertEquals("Action", m.getGenre());
        assertEquals(downloaded, m.getImagePaths());
        verify(repo).save(any(Movie.class));
    }

    @Test
    void addByTitle_shouldThrowNotFound_whenOmdbReturnsNull() throws Exception {
        when(omdb.fetchByTitle(anyString())).thenReturn(Mono.empty());
        when(tmdb.search(anyString())).thenReturn(Mono.just(new TmdbSearchDTO(List.of())));
        assertThrows(ResponseStatusException.class, () -> service.addByTitle("NoSuchMovie"));
    }

    @Test
    void addByTitle_shouldThrowInternalError_whenImageDownloadFails() throws Exception {
        String title = "Test";
        OmdbMovieDTO omdbMovieDTO = new OmdbMovieDTO(title, "2000", "Director", "Genre", "id456");
        TmdbSearchDTO.Result tmdbResult = new TmdbSearchDTO.Result(title, 42);
        TmdbSearchDTO tmdbSearchDTO = new TmdbSearchDTO(List.of(tmdbResult));
        TmdbImagesDTO.FilePath fp = new TmdbImagesDTO.FilePath("/poster2.jpg");
        TmdbImagesDTO imgDTO = new TmdbImagesDTO(List.of(fp), List.of(fp));
        when(omdb.fetchByTitle(title)).thenReturn(Mono.just(omdbMovieDTO));
        when(tmdb.search(title)).thenReturn(Mono.just(tmdbSearchDTO));
        when(tmdb.images(42)).thenReturn(Mono.just(imgDTO));
        when(downloader.download(anyList())).thenThrow(new Exception("fail"));
        assertThrows(ResponseStatusException.class, () -> service.addByTitle(title));
    }

    @Test
    void list_shouldCallRepoFindAll() {
        Pageable pageable = Pageable.unpaged();
        Page<Movie> page = mock(Page.class);
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.list(pageable));
        verify(repo).findAll(pageable);
    }
}

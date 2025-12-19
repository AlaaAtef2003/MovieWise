package WhiteBoxTest;

import InOut.FileReader;
import Models.Movie;
import Models.User;
import Services.InputValidator;
import Services.RecommendMoviesValidator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * White-Box Data-Flow Testing
 *
 * Coverage:
 * - All-Defs
 * - All-Uses
 * - All-DU-Paths
 */
public class DataFlowTestingTest {

    private static final String TEST_FILE = "movies_test.txt";

    // =====================================================
    // CLEAN-UP AFTER EACH TEST
    // =====================================================
    @AfterEach
    void cleanUp() throws Exception {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
    }

    // =====================================================
    // FUNCTION 1: FileReader.readMovies()
    // =====================================================

    @Test
    void testReadMovies_AllDefs() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies(TEST_FILE);

        assertNotNull(movies, "Movies list should not be null");
        assertFalse(movies.isEmpty(), "Movies list should not be empty");
    }

    @Test
    void testReadMovies_AllUses() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies(TEST_FILE);

        Movie movie = movies.get(0);
        assertNotNull(movie.getMovieId(), "Movie ID should not be null");
        assertNotNull(movie.getTitle(), "Movie title should not be null");
    }

    @Test
    void testReadMovies_AllDUPaths() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies(TEST_FILE);

        for (Movie m : movies) {
            assertFalse(m.getMovieId().isEmpty(), "Movie ID should not be empty");
        }
    }

    @Test
    void testReadMovies_InvalidFile() {
        FileReader reader = new FileReader();
        assertThrows(Exception.class, () -> reader.readMovies("non_existing_file.txt"));
    }

    // =====================================================
    // FUNCTION 2: InputValidator.validateMovie()
    // =====================================================

    @Test
    void testValidateMovie_AllDefs() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        Movie movie = new Movie("Avatar", "A123", List.of("Action"));
        validator.validateMovie(movie, usedIds);

        assertEquals(1, usedIds.size(), "Used IDs should contain one ID");
    }

    @Test
    void testValidateMovie_AllDUPaths() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        validator.validateMovie(new Movie("Jaws", "J001", List.of("Horror")), usedIds);
        validator.validateMovie(new Movie("Joker", "J002", List.of("Drama")), usedIds);

        assertEquals(2, usedIds.size(), "Used IDs should contain two IDs");
    }

    @Test
    void testValidateMovie_DuplicateId() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        Movie movie1 = new Movie("Movie1", "M001", List.of("Action"));
        Movie movie2 = new Movie("Movie2", "M001", List.of("Drama")); // duplicate ID

        validator.validateMovie(movie1, usedIds);
        assertThrows(IllegalArgumentException.class, () -> validator.validateMovie(movie2, usedIds),
                "Should throw exception for duplicate movie ID");
    }

    // =====================================================
    // FUNCTION 3: RecommendMoviesValidator.recommendMovies()
    // =====================================================

    @Test
    void testRecommendMovies_AllDefs() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678", List.of("M001"));
        Movie movie = new Movie("Matrix", "M001", List.of("Sci-Fi"));

        List<String> recommendations = recommender.recommendMovies(user, List.of(movie));

        assertNotNull(recommendations, "Recommendations list should not be null");
    }

    @Test
    void testRecommendMovies_AllUses() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678", List.of("M001"));

        Movie m1 = new Movie("Matrix", "M001", List.of("Sci-Fi"));
        Movie m2 = new Movie("Avatar", "M002", List.of("Sci-Fi"));

        List<String> recommendations = recommender.recommendMovies(user, List.of(m1, m2));

        assertFalse(recommendations.isEmpty(), "Recommendations should not be empty");
    }

    @Test
    void testRecommendMovies_AllDUPaths() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678", List.of("M001"));

        Movie m1 = new Movie("Matrix", "M001", List.of("Sci-Fi"));
        Movie m2 = new Movie("Interstellar", "M002", List.of("Sci-Fi"));

        List<String> recommendations = recommender.recommendMovies(user, List.of(m1, m2));

        assertTrue(recommendations.size() >= 1, "There should be at least one recommendation");
    }

    @Test
    void testRecommendMovies_EmptyMovieList() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();
        User user = new User("Ali", "12345678", List.of());

        List<String> recommendations = recommender.recommendMovies(user, List.of());
        assertTrue(recommendations.isEmpty(), "Recommendations should be empty for empty movie list");
    }

    // =====================================================
    // HELPER METHOD
    // =====================================================

    private void createValidMoviesFile() throws Exception {
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("Avatar,A123\n");
            writer.write("Action\n");

            writer.write("Titanic,T456\n");
            writer.write("Drama\n");
        }
    }
}

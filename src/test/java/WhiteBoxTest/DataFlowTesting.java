package WhiteBoxTest;

import Integration.Integration;
import Models.Movie;
import Models.User;
import Services.InputValidator;
import Services.RecommendMoviesValidator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Dataflow White-Box Testing
 * Covers:
 * - All-Defs
 * - All-Uses
 * - All-DU-Paths
 */

public class DataFlowTesting {
    /* ===================== ALL-DEFS COVERAGE ===================== */

    /**
     * usersFile definition used in validation and recommendation
     */
    @Test
    void testAllUses_InvalidUserData() {
        Integration integration = new Integration();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> integration.outputFile(
                "src/test/resources/invalid_users.txt",
                "src/test/resources/movies.txt",
                "src/test/resources/output.txt"));

        assertTrue(ex.getMessage().toLowerCase().contains("user"));
    }

    /* ===================== ALL-DU-PATHS COVERAGE ===================== */

    /**
     * RecommendMoviesValidator
     * DU paths:
     * likedGenres -> contains()
     * recommendedTitles -> add()
     */
    @Test
    void testAllDUPaths_RecommendMoviesValidator() {
        Movie actionMovie = new Movie("Action Hero", "A001", List.of("Action"));
        Movie dramaMovie = new Movie("Sad Story", "D001", List.of("Drama"));

        User user = new User("Ahmed", "12345678", List.of("A001"));

        RecommendMoviesValidator validator = new RecommendMoviesValidator();
        List<String> recommendations = validator.recommendMovies(user, List.of(actionMovie, dramaMovie));

        assertEquals(1, recommendations.size());
        assertEquals("Action Hero", recommendations.get(0));
    }

    /**
     * InputValidator Movie ID uniqueness
     * usedMovieIds: DEF -> contains() -> add()
     */
    @Test
    void testAllDUPaths_InputValidator_MovieIds() {
        InputValidator validator = new InputValidator();
        Set<String> usedMovieIds = new HashSet<>();

        Movie movie = new Movie("Test Movie", "TM001", List.of("Comedy"));

        assertDoesNotThrow(() -> validator.validateMovie(movie, usedMovieIds));
        assertTrue(usedMovieIds.contains("TM001"));
    }

    /**
     * InputValidator User ID uniqueness
     * usedUserIds: DEF -> contains() -> add()
     */
    @Test
    void testAllDUPaths_InputValidator_UserIds() {
        InputValidator validator = new InputValidator();
        Set<String> usedUserIds = new HashSet<>();

        User user = new User("Sara", "87654321", List.of("A001"));

        assertDoesNotThrow(() -> validator.validateUser(user, usedUserIds));
        assertTrue(usedUserIds.contains("87654321"));
    }

}

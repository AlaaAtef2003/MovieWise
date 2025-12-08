package WhiteBoxTest.StatementCoverage;

import Models.Movie;
import Models.User;
import Services.RecommendMoviesValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendMoviesStatementCoverageTest {

    RecommendMoviesValidator validator = new RecommendMoviesValidator();

    // ================================================================
    // 1. User likes no movies → likedGenres remains empty → no output
    // ================================================================
    @Test
    void testNoLikedMovies_NoRecommendations() {
        User u = new User("John", "123", List.of());
        List<Movie> movies = List.of(
                new Movie("Matrix", "M123", List.of("Action"))
        );

        List<String> result = validator.recommendMovies(u, movies);

        assertTrue(result.isEmpty());
    }

    // ============================================================================
    // 2. User likes a movie that exists → its genres are collected → recommendations
    // ============================================================================
    @Test
    void testLikesMovie_GetsRecommendations() {
        User u = new User("Sam", "111", List.of("M1"));

        Movie m1 = new Movie("Matrix", "M1", List.of("Action"));
        Movie m2 = new Movie("John Wick", "M2", List.of("Action"));
        Movie m3 = new Movie("Titanic", "M3", List.of("Romance"));

        List<Movie> movies = List.of(m1, m2, m3);

        List<String> result = validator.recommendMovies(u, movies);

        assertEquals(List.of("Matrix", "John Wick"), result);
    }

    // ======================================================================================
    // 3. User likes an ID not found among movies → likedGenres stays empty → no recommendation
    // ======================================================================================
    @Test
    void testLikedMovieNotFound_NoRecommendations() {
        User u = new User("Sara", "222", List.of("UNKNOWN"));

        List<Movie> movies = List.of(
                new Movie("Matrix", "M1", List.of("Action"))
        );

        List<String> result = validator.recommendMovies(u, movies);

        assertTrue(result.isEmpty());
    }

    // ======================================================================================
    // 4. Movies with multiple genres → ensure *all matching genres* trigger recommendations
    // ======================================================================================
    @Test
    void testMovieWithMultipleGenres_MatchMultiple() {
        User u = new User("Mark", "333", List.of("X1"));

        Movie liked = new Movie("Epic Movie", "X1", List.of("Fantasy", "Adventure"));
        Movie other1 = new Movie("Lord of the Rings", "X2", List.of("Fantasy"));
        Movie other2 = new Movie("Indiana Jones", "X3", List.of("Adventure"));
        Movie noMatch = new Movie("Titanic", "X4", List.of("Romance"));

        List<Movie> movies = List.of(liked, other1, other2, noMatch);

        List<String> result = validator.recommendMovies(u, movies);

        assertEquals(List.of("Epic Movie", "Lord of the Rings", "Indiana Jones"), result);
    }

    // ======================================================================================
    // 5. Duplicate genre matches → ensure LinkedHashSet prevents duplicate titles
    // ======================================================================================
    @Test
    void testDuplicateGenreAvoidDuplicateTitles() {
        User u = new User("Lisa", "444", List.of("M1"));

        Movie liked = new Movie("Matrix", "M1", List.of("Action", "SciFi"));
        // Both genres match same movie; we should still get only 1 entry
        Movie same = new Movie("Edge of Tomorrow", "M2", List.of("Action", "SciFi"));

        List<Movie> movies = List.of(liked, same);

        List<String> result = validator.recommendMovies(u, movies);

        // Output order follows insertion order due to LinkedHashSet
        assertEquals(List.of("Matrix", "Edge of Tomorrow"), result);
    }
}

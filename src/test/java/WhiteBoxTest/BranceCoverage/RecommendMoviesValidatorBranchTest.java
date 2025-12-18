package WhiteBoxTest.BranceCoverage;



import Models.Movie;
import Models.User;
import Services.RecommendMoviesValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendMoviesValidatorBranchTest {

    RecommendMoviesValidator validator = new RecommendMoviesValidator();

    @Test
    void testNoLikedMovies() {
        User u = new User("John", "123", List.of());
        Movie m = new Movie("Matrix", "M123", List.of("Action"));
        List<String> rec = validator.recommendMovies(u, List.of(m));
        assertTrue(rec.isEmpty());
    }

    @Test
    void testOneLikedMovieRecommendMatchingGenre() {
        User u = new User("John", "123", List.of("M123"));
        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        Movie m2 = new Movie("John Wick", "JW111", List.of("Action"));
        List<String> rec = validator.recommendMovies(u, List.of(m1, m2));
        assertEquals(List.of("Matrix", "John Wick"), rec);
    }

    @Test
    void testLikedMovieNoOtherMatches() {
        User u = new User("John", "123", List.of("M123"));
        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        Movie m2 = new Movie("B", "B001", List.of("Drama"));
        List<String> rec = validator.recommendMovies(u, List.of(m1, m2));
        assertEquals(List.of("Matrix"), rec);
    }

    @Test
    void testMultipleLikedMoviesMultipleGenres() {
        User u = new User("John", "123", List.of("M123", "B001"));
        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        Movie m2 = new Movie("B", "B001", List.of("Drama"));
        Movie m3 = new Movie("C", "C001", List.of("Action","Drama"));
        List<String> rec = validator.recommendMovies(u, List.of(m1, m2, m3));
        assertEquals(List.of("Matrix","B","C"), rec);
    }

    @Test
    void testLikedMovieNotInList() {
        User u = new User("John", "123", List.of("XYZ"));
        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        List<String> rec = validator.recommendMovies(u, List.of(m1));
        assertTrue(rec.isEmpty());
    }
}

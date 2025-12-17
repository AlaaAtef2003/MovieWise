package WhiteBoxTest.pathCoverage;

import static org.junit.jupiter.api.Assertions.*;
import Models.Movie;
import Models.User;
import Services.InputValidator;
import Services.RecommendMoviesValidator;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RecommendedMoviesValidatorPathCoverage {
	  RecommendMoviesValidator validator = new RecommendMoviesValidator();
	   @Test
	    void testNoLikedMovies() {
	        User u = new User("John", "12345678A", List.of());
	        Movie m = new Movie("Matrix", "M123", List.of("Action"));
	        List<String> rec = validator.recommendMovies(u, List.of(m));
	        assertEquals(rec.size(),0);
	    }
	   @Test
	    void testOneLikedMovieRecommendMatchingGenre() {
	        User u = new User("John", "12345678A", List.of("M123"));
	        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
	        Movie m2 = new Movie("John Wick", "JW111", List.of("Action"));
	        List<String> rec = validator.recommendMovies(u, List.of(m1, m2));
	        assertEquals(List.of("Matrix", "John Wick"), rec);
	    }

	    @Test
	    void testLikedMovieNoOtherMatches() {
	        User u = new User("John", "12345678A", List.of("M123"));
	        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
	        Movie m2 = new Movie("B", "B001", List.of("Drama"));
	        List<String> rec = validator.recommendMovies(u, List.of(m1, m2));
	        assertEquals(List.of("Matrix"), rec);
	    }

	    @Test
	    void testMultipleLikedMoviesMultipleGenres() {
	        User u = new User("John", "12345678A", List.of("M123", "B001"));
	        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
	        Movie m2 = new Movie("B", "B001", List.of("Drama"));
	        Movie m3 = new Movie("C", "C001", List.of("Action","Drama"));
	        List<String> rec = validator.recommendMovies(u, List.of(m1, m2, m3));
	        assertEquals(List.of("Matrix","B","C"), rec);
	    }

	    @Test
	    void testLikedMovieNotInList() {
	        User u = new User("John", "12345678A", List.of("XYZ"));
	        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
	        List<String> rec = validator.recommendMovies(u, List.of(m1));
	        assertTrue(rec.isEmpty());
	    }
}

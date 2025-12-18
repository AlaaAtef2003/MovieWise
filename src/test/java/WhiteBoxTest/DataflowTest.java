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
import java.io.FileReader;
import java.io.FileWriter;
import InOut.FileReader;

/**
 * Dataflow White-Box Testing
 * Covers:
 * - All-Defs
 * - All-Uses
 * - All-DU-Paths
 */

public class DataflowTest {
    // =====================================================
    // FUNCTION 1: FileReader.readMovies()
    // =====================================================

    // ---------- ALL-DEFS ----------
    @Test
    public void testReadMovies_AllDefs() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies("movies_test.txt");

        assertNotNull(movies);  "movies"
        assertFalse(movies.isEmpty()); s movies.add()
    }

    // ---------- ALL-USES ----------
    @Test
    public void testReadMovies_AllUses() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies("movies_test.txt");

        Movie m = movies.get(0); ie"
        assertNotNull(m.getMovieId()); movieId
        assertNotNull(m.getTitle()); e
    }

    // ---------- ALL-DU-PATHS ----------
    @Test
    public void testReadMovies_AllDUPaths() throws Exception {
        createValidMoviesFile();

        FileReader reader = new FileReader();
        List<Movie> movies = reader.readMovies("movies_test.txt");

        for (Movie m : movies) {
            assertTrue(m.getMovieId().length() > 0); // def → multiple uses
        }
    }

    // =====================================================
    // FUNCTION 2: InputValidator.validateMovie()
    // =====================================================

    // ---------- ALL-DEFS ----------
    @Test
    public void testValidateMovie_AllDefs() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        Movie movie = new Movie("Avatar", "A123", List.of("Action"));

        validator.validateMovie(movie, usedIds);

        assertEquals(1, usedIds.size()); onfirms definition used
    }

    // ---------- ALL-USES ----------
    @Test
    public void testValidateMovie_AllUses() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        Movie movie = new Movie("Titanic", "T456", List.of("Drama"));

        validator.validateMovie(movie, usedIds);

        assertTrue(usedIds.contains("456")); // use of extracted ID numbers
    }

    // ---------- ALL-DU-PATHS ----------
    @Test
    public void testValidateMovie_AllDUPaths() {
        InputValidator validator = new InputValidator();
        Set<String> usedIds = new HashSet<>();

        Movie m1 = new Movie("Jaws", "J001", List.of("Horror"));
        Movie m2 = new Movie("Joker", "J002", List.of("Drama"));

        validator.validateMovie(m1, usedIds);
        validator.validateMovie(m2, usedIds);

        assertEquals(2, usedIds.size()); ultiple DU paths
    }

    // =====================================================
    // FUNCTION 3: RecommendMoviesValidator.recommendMovies()
    // =====================================================

    // ---------- ALL-DEFS ----------
    @Test
    public void testRecommendMovies_AllDefs() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678",
                List.of("M001"));

        Movie movie = new Movie("Matrix", "M001",
                List.of("Sci-Fi"));

        List<String> result = tNull(result); // uses defined recommendedTitles
    }

    // ---------- ALL-USES ----------
    @Test
    public void testRecommendMovies_AllUses() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678",
                List.of("M001"));

        Movie m1 = new Movie("Matrix", "M001",
                List.of("Sci-Fi"));
        Movie m2 = new Movie("Avatar", "M002",
                List.of("Sci-Fi"));

        List<String> result = recommender.recommendMovies(user, List.of(m1, m2));
 ue(result.contains("Avatar")); // use of likedGenres
    } 
    // ---------- ALL-DU-PATHS ----------
    @Test
    public void testRecommendMovies_AllDUPaths() {
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        User user = new User("Ali", "12345678",
                List.of("M001"));

        Movie m1 = new Movie("Matrix", "M001",
                List.of("Sci-Fi"));
        Movie m2 = new Movie("Interstellar", "M002",
                List.of("Sci-Fi"));

        List<String> result = recommender.recommendMovies(user, List.of(m1, m2));

        assertEquals(2, resul 

    // ====================== THOD (creates test movie file)
    // =====================================================
    private void createValidMoviesFile() throws Exception {
        FileWriter writer = new FileWriter("movies_test.txt");

        writer.write("Avatar,A123\n");
        writer.write("Action\n");

        writer.write("Titanic,T456\n");
        writer.write("Drama\n");

        writer.close();
    }

}

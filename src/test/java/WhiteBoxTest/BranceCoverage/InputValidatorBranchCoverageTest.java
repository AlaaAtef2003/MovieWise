package WhiteBoxTest.BranceCoverage;



import Models.Movie;
import Models.User;
import Services.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorBranchCoverageTest {

    private InputValidator validator;
    private Set<String> usedMovieIds;
    private Set<String> usedUserIds;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
        usedMovieIds = new HashSet<>();
        usedUserIds = new HashSet<>();
    }

    // Branch 1: Valid movie passes all checks
    @Test
    void testValidMovie() {
        Movie m = new Movie("Matrix", "M123", null);
        assertDoesNotThrow(() -> validator.validateMovie(m, usedMovieIds));
        assertTrue(usedMovieIds.contains("123"));
    }

    // Branch 2: Movie title invalid
    @Test
    void testMovieInvalidTitle() {
        Movie m = new Movie("matrix", "M123", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedMovieIds));
        assertTrue(ex.getMessage().contains("ERROR: Movie Title matrix is wrong"));
    }

    // Branch 3: Movie ID letters mismatch
    @Test
    void testMovieIdLettersMismatch() {
        Movie m = new Movie("Matrix", "X123", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedMovieIds));
        assertTrue(ex.getMessage().contains("ERROR: Movie Id letters X123 are wrong"));
    }

    // Branch 4: Movie ID numbers not 3 digits
    @Test
    void testMovieIdNumbersInvalid() {
        Movie m = new Movie("Matrix", "M12", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedMovieIds));
        assertTrue(ex.getMessage().contains("ERROR: Movie Id numbers M12 aren’t unique"));
    }

    // Branch 5: Movie ID numbers duplicate
    @Test
    void testMovieIdNumbersDuplicate() {
        usedMovieIds.add("123");
        Movie m = new Movie("Matrix", "M123", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedMovieIds));
        assertTrue(ex.getMessage().contains("ERROR: Movie Id numbers M123 aren’t unique"));
    }

    // Branch 6: User name starts with space
    @Test
    void testUserNameStartsWithSpace() {
        User u = new User(" John", "12345678A", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedUserIds));
        assertTrue(ex.getMessage().contains("ERROR: User Name  John is wrong"));
    }

    // Branch 7: User ID duplicate
    @Test
    void testUserIdDuplicate() {
        usedUserIds.add("12345678A");
        User u = new User("John", "12345678A", null);
        Exception ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedUserIds));
        assertTrue(ex.getMessage().contains("ERROR: User Id 12345678A is wrong"));
    }
}

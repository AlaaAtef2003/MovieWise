package WhiteBoxTest.StatementCoverage;

import Models.Movie;
import Models.User;
import Services.InputValidator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorStatementCoverageTest {

    InputValidator validator = new InputValidator();

    // ======================================================
    // MOVIE VALIDATION — STATEMENT COVERAGE
    // ======================================================

    @Test
    void testValidateMovie_Valid() {
        Movie m = new Movie("Matrix Reloaded", "MR123", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        assertDoesNotThrow(() -> validator.validateMovie(m, usedIds));
    }

    // ---------- validateMovieTitle ----------

    @Test
    void testValidateMovieTitle_Invalid() {
        Movie m = new Movie("matrix reloaded", "MR123", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedIds));

        assertEquals("ERROR: Movie Title matrix reloaded is wrong", ex.getMessage());
    }

    // ---------- validateMovieIdLetters ----------

    @Test
    void testValidateMovieIdLetters_InvalidPrefix() {
        Movie m = new Movie("Matrix", "XX123", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedIds));

        assertEquals("ERROR: Movie Id letters XX123 are wrong", ex.getMessage());
    }

    @Test
    void testValidateMovieIdLetters_InvalidLength() {
        Movie m = new Movie("Matrix", "M12", List.of("Action")); // missing required digits
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedIds));

        assertEquals("ERROR: Movie Id letters M12 are wrong", ex.getMessage());
    }

    // ---------- ensureMovieIdIsUnique ----------

    @Test
    void testEnsureMovieIdIsUnique_InvalidNumbers() {
        Movie m = new Movie("Matrix", "M12A", List.of("Action")); // invalid digits
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedIds));

        assertEquals("ERROR: Movie Id numbers M12A aren’t unique", ex.getMessage());
    }

    @Test
    void testEnsureMovieIdIsUnique_Duplicate() {
        Set<String> usedIds = new HashSet<>();
        usedIds.add("123");  // stored number portion

        Movie m = new Movie("Matrix", "M123", List.of("Action"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateMovie(m, usedIds));

        assertEquals("ERROR: Movie Id numbers M123 aren’t unique", ex.getMessage());
    }

    // ======================================================
    // USER VALIDATION — STATEMENT COVERAGE
    // ======================================================

    @Test
    void testValidateUser_Valid() {
        User u = new User("John Smith", "12345678A", List.of());
        Set<String> usedIds = new HashSet<>();

        assertDoesNotThrow(() -> validator.validateUser(u, usedIds));
    }

    // ---------- validateUserName ----------

    @Test
    void testValidateUserName_LeadingSpace() {
        User u = new User(" John", "12345678A", List.of());
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedIds));

        assertEquals("ERROR: User Name  John is wrong", ex.getMessage());
    }

    @Test
    void testValidateUserName_InvalidCharacters() {
        User u = new User("John2", "12345678A", List.of());
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedIds));

        assertEquals("ERROR: User Name John2 is wrong", ex.getMessage());
    }

    // ---------- validateUserIdFormat ----------

    @Test
    void testValidateUserIdFormat_InvalidLength() {
        User u = new User("John", "12345", List.of());
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedIds));

        assertEquals("ERROR: User Id 12345 is wrong", ex.getMessage());
    }

    @Test
    void testValidateUserIdFormat_InvalidChars() {
        User u = new User("John", "12345@78", List.of());
        Set<String> usedIds = new HashSet<>();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedIds));

        assertEquals("ERROR: User Id 12345@78 is wrong", ex.getMessage());
    }

    // ---------- checkUserIdUniqueness ----------

    @Test
    void testCheckUserIdUniqueness_Duplicate() {
        Set<String> usedIds = new HashSet<>();
        usedIds.add("12345678A");

        User u = new User("John", "12345678A", List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validateUser(u, usedIds));

        assertEquals("ERROR: User Id 12345678A is wrong", ex.getMessage());
    }
}

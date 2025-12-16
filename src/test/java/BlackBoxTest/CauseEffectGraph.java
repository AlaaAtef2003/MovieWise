package BlackBoxTest;

import Models.Movie;
import Models.User;
import Services.InputValidator;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Cause–Effect Graph Testing
 * --------------------------

 * Causes (Input Conditions):
 *  - Movie:
 *      C1: Movie title format is valid
 *      C2: Movie ID contains all capital letters from the title
 *      C3: Movie ID ends with exactly three unique digits
 *      C4: Movie ID numbers are unique across all movies
 *
 *  - User:
 *      C5: User name format is valid (letters and spaces only)
 *      C6: User ID format is valid (8 digits with optional letter)
 *      C7: User ID is unique across all users
 *
 * Effects (System Behavior):
 *  - E1: Movie is accepted and validated successfully
 *  - E2: User is accepted and validated successfully
 *  - E3: Validation fails and an exception is thrown
 *
 * Cause–Effect Relationships:
 *  - If (C1 AND C2 AND C3 AND C4) are true → E1 occurs
 *  - If (C5 AND C6 AND C7) are true → E2 occurs
 *  - If any cause is false → E3 occurs
 */

public class CauseEffectGraph {

    InputValidator validator = new InputValidator();

    // ----------- MOVIE CAUSE–EFFECT -----------

    @Test
    public void movie_AllCausesTrue() {
        Movie movie = new Movie("Star Wars", "SW001", List.of("Action"));
        Set<String> ids = new HashSet<>();

        assertDoesNotThrow(
                () -> validator.validateMovie(movie, ids));
    }

    @Test
    public void movie_InvalidTitle() {
        Movie movie = new Movie("star wars", "SW001", List.of("Action"));
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateMovie(movie, ids));
    }

    @Test
    public void movie_NotAllCapitalLettersFromTitle() {
        Movie movie = new Movie("star wars", "SS001", List.of("Action"));
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateMovie(movie, ids));
    }

    @Test
    public void movie_NotExactly3Digits() {
        Movie movie = new Movie("star wars", "SW00", List.of("Action"));
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateMovie(movie, ids));
    }

    @Test
    public void movie_DuplicateId() {
        Movie movie1 = new Movie("Star Wars", "SW001", List.of("Action"));
        Movie movie2 = new Movie("Star Trek", "ST001", List.of("Sci-Fi"));

        Set<String> ids = new HashSet<>();

        validator.validateMovie(movie1, ids);

        assertThrows(RuntimeException.class,
                () -> validator.validateMovie(movie2, ids));
    }

    // ----------- USER CAUSE–EFFECT -----------

    @Test
    public void user_AllCausesTrue() {
        User user = new User("Ahmed Ali", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertDoesNotThrow(
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void user_InvalidName() {
        User user = new User("Ahmed1", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void user_DuplicateId() {
        User user1 = new User("Ahmed", "12345678", null);
        User user2 = new User("Ali", "12345678", null);

        Set<String> ids = new HashSet<>();

        validator.validateUser(user1, ids);

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user2, ids));
    }

    @Test
    public void user_NotExactly8Digits() {
        User user1 = new User("Ahmed", "1234567", null);

        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user1, ids));
    }
}

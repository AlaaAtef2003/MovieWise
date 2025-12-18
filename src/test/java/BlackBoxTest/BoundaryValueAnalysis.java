package BlackBoxTest;

import Models.Movie;
import Models.User;
import Services.InputValidator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoundaryValueAnalysis {

    InputValidator validator = new InputValidator();

    // ---------------- USER ID BOUNDARY ----------------

    @Test
    public void userId_LessThan8Digits() {
        User user = new User("Ahmed", "1234567", null);
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void userId_Exactly8Digits() {
        User user = new User("Ahmed", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertDoesNotThrow(
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void userId_MoreThan8Digits() {
        User user = new User("Ahmed", "123456789", null);
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user, ids));
    }

    // ---------------- USER NAME BOUNDARY ----------------

    @Test
    public void userName_Empty() {
        User user = new User("", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void userName_SingleWord() {
        User user = new User("Ahmed", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertDoesNotThrow(
                () -> validator.validateUser(user, ids));
    }

    @Test
    public void userName_Start_Space() {
        User user = new User(" Ahmed", "12345678", null);
        Set<String> ids = new HashSet<>();

        assertThrows(RuntimeException.class,
                () -> validator.validateUser(user, ids));
    }

    // -------------- MOVIE ID BOUNDARY-------------

    @Test
    public void movieId_LessThan3Digits() {
        Movie movie = new Movie("Star Wars", "SW01", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        assertThrows(RuntimeException.class, () ->
                validator.validateMovie(movie, usedIds));
    }

    @Test
    public void movieId_MoreThan3Digits() {
        Movie movie = new Movie("Star Wars", "SW0001", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        assertThrows(RuntimeException.class, () ->
                validator.validateMovie(movie, usedIds));
    }

    @Test
    public void movieId_Exactly3Digits() {
        Movie movie = new Movie("Star Wars", "SW001", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        assertDoesNotThrow(
                () -> validator.validateMovie(movie, usedIds));
    }

    @Test
    public void movieId_NonDigitCharacters() {
        Movie movie = new Movie("Star Wars", "SW0A1", List.of("Action"));
        Set<String> usedIds = new HashSet<>();

        assertThrows(RuntimeException.class, () ->
                validator.validateMovie(movie, usedIds));
    }
}

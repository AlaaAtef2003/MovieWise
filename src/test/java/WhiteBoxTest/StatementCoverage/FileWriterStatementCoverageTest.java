package WhiteBoxTest.StatementCoverage;
import InOut.FileWriter;
import Models.Movie;
import Models.User;
import Services.RecommendMoviesValidator;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterStatementCoverageTest {

    FileWriter fileWriter = new FileWriter();


    RecommendMoviesValidator recommender = new RecommendMoviesValidator();

    // Helper method to read entire file as string
    private String readAll(String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // ==============================================================================
    // Test 1: Single user + no recommendations (empty liked IDs)
    // ==============================================================================
    @Test
    void testWriteRecommendations_SingleUser_NoRecommendations() throws Exception {
        String outPath = "out_single_user.txt";

        User u = new User("John", "12345678A", List.of());
        List<User> users = List.of(u);

        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        List<Movie> movies = List.of(m1);

        fileWriter.writeRecommendations(outPath, users, movies, recommender);

        String content = readAll(outPath);

        assertEquals(
                "John,12345678A\n\n",
                content
        );
    }

    // ==============================================================================
    // Test 2: Single user + one recommendation
    // ==============================================================================
    @Test
    void testWriteRecommendations_OneRecommendedMovie() throws Exception {
        String outPath = "out_one_recommend.txt";

        User u = new User("Sam", "87654321B", List.of("M123"));

        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        Movie m2 = new Movie("John Wick", "JW111", List.of("Action"));

        List<User> users = List.of(u);
        List<Movie> movies = List.of(m1, m2);

        fileWriter.writeRecommendations(outPath, users, movies, recommender);

        String content = readAll(outPath);

        assertEquals(
                "Sam,87654321B\nMatrix,John Wick\n",
                content
        );
    }

    // ==============================================================================
    // Test 3: Two users, mixed results
    // ==============================================================================
    @Test
    void testWriteRecommendations_TwoUsers_MixedOutput() throws Exception {
        String outPath = "out_two_users.txt";

        User u1 = new User("Sara", "11111111A", List.of("A001"));
        User u2 = new User("Mark", "22222222B", List.of());  // no recommendations

        Movie m1 = new Movie("A", "A001", List.of("Drama"));
        Movie m2 = new Movie("B", "B001", List.of("Drama"));
        Movie m3 = new Movie("C", "C001", List.of("Action"));

        List<User> users = Arrays.asList(u1, u2);
        List<Movie> movies = Arrays.asList(m1, m2, m3);

        fileWriter.writeRecommendations(outPath, users, movies, recommender);

        String content = readAll(outPath);

        assertEquals(
                """
                        Sara,11111111A
                        A,B
                        Mark,22222222B
                        
                        """,
                content
        );
    }

    // ==============================================================================
    // Test 4: FileWriter closes file correctly (statement coverage)
    // ==============================================================================
    @Test
    void testWriteRecommendations_FileClosedSuccessfully() throws Exception {
        String outPath = "out_close_test.txt";

        User u = new User("Test", "99999999Z", List.of());
        List<User> users = List.of(u);

        Movie m = new Movie("X", "X001", List.of("SciFi"));
        List<Movie> movies = List.of(m);

        assertDoesNotThrow(() ->
                fileWriter.writeRecommendations(outPath, users, movies, recommender)
        );

        // file should exist and contain:
        // Test,99999999Z
        // <empty line>
        String content = readAll(outPath);
        assertEquals(
                "Test,99999999Z\n\n",
                content
        );
    }
}

package WhiteBoxTest.BranchCoverage;


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

public class FileWriterBranchCoverageTest {

    private final FileWriter fileWriter = new FileWriter();
    private final RecommendMoviesValidator recommender = new RecommendMoviesValidator();

    // Helper method to read entire file
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

    // ===============================
    // Test 1: Single user, no recommendations
    // ===============================
    @Test
    void testSingleUserNoRecommendations() throws Exception {
        String outPath = "out_single_user.txt";
        User u = new User("John", "12345678A", List.of());
        Movie m = new Movie("Matrix", "M123", List.of("Action"));

        fileWriter.writeRecommendations(outPath, List.of(u), List.of(m), recommender);

        String content = readAll(outPath);
        assertEquals("John,12345678A\n\n", content);
    }

    // ===============================
    // Test 2: Single user, one recommendation
    // ===============================
    @Test
    void testSingleUserOneRecommendation() throws Exception {
        String outPath = "out_one_recommend.txt";
        User u = new User("Sam", "87654321B", List.of("M123"));

        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        Movie m2 = new Movie("John Wick", "JW111", List.of("Action"));

        fileWriter.writeRecommendations(outPath, List.of(u), List.of(m1, m2), recommender);

        String content = readAll(outPath);
        assertEquals("Sam,87654321B\nMatrix,John Wick\n", content);
    }

    // ===============================
    // Test 3: Two users, mixed recommendations
    // ===============================
//    @Test
//    void testTwoUsersMixedOutput() throws Exception {
//        String outPath = "out_two_users.txt";
//
//        User u1 = new User("Sara", "11111111A", List.of("A001"));
//        User u2 = new User("Mark", "22222222B", List.of()); // no recommendations
//
//        Movie m1 = new Movie("A", "A001", List.of("Drama"));
//        Movie m2 = new Movie("B", "B001", List.of("Drama"));
//        Movie m3 = new Movie("C", "C001", List.of("Action"));
//
//        fileWriter.writeRecommendations(outPath, Arrays.asList(u1, u2), Arrays.asList(m1, m2, m3), recommender);
//
//        String content = readAll(outPath);
//        assertEquals(
//                """
//                Sara,11111111A
//                A,B
//                Mark,22222222B
//
//               \s""", content);
//    }

    // ===============================
    // Test 4: FileWriter closes file correctly
    // ===============================
    @Test
    void testFileClosedSuccessfully() throws Exception {
        String outPath = "out_close_test.txt";

        User u = new User("Test", "99999999Z", List.of());
        Movie m = new Movie("X", "X001", List.of("SciFi"));

        assertDoesNotThrow(() -> fileWriter.writeRecommendations(outPath, List.of(u), List.of(m), recommender));

        String content = readAll(outPath);
        assertEquals("Test,99999999Z\n\n", content);
    }
}

package WhiteBoxTest.StatementCoverage;

import InOut.FileReader;
import Models.Movie;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderStatementCoverageTest {

    FileReader fileReader = new FileReader();

    // =========================================================================
    // readMovies() — Statement Coverage + Exact Message Verification
    // =========================================================================

    @Test
    void testReadMovies_EmptyLineSkipped_ValidMovie() throws Exception {
        String path = "movies_valid.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("\n");
            fw.write("Matrix,M123\n");
            fw.write("Action,SciFi\n");
        }
        List<Movie> movies = fileReader.readMovies(path);
        assertEquals(1, movies.size());
    }

    @Test
    void testReadMovies_InvalidMovieLineFormat_ExactMessage() {
        String path = "movies_invalid_format.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("InvalidLineNoComma\n");
            fw.write("Action\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readMovies(path));

        // FileReader wraps the original exception, so check the cause
        assertEquals("Invalid movie line format", ex.getCause().getMessage());
    }

    // =========================================================================
    // parseMovie() — Exact Message
    // =========================================================================

    @Test
    void testParseMovie_Invalid_NoComma_ExactMessage() {
        String path = "movies_parse_invalid.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("WrongFormat\n");
            fw.write("Drama\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readMovies(path));

        assertEquals("Invalid movie line format", ex.getCause().getMessage());
    }

    // =========================================================================
    // readUsers() — Statement Coverage + Exact Message Verification
    // =========================================================================

    @Test
    void testReadUsers_InvalidUserFormat_ExactMessage() {
        String path = "users_invalid_format.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("InvalidUserLine\n");   // No comma → parseUser failure
            fw.write("M1,M2\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readUsers(path));

        assertEquals("Invalid user line format", ex.getCause().getMessage());
    }

    @Test
    void testParseUser_LeadingSpace_ExactMessage() {
        String path = "users_leading_space.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(" John,12345678A\n");  // Leading space triggers error
            fw.write("M1,M2\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readUsers(path));

        assertEquals("ERROR: User Name  John is wrong", ex.getCause().getMessage());
    }

    @Test
    void testParseUser_MissingComma_ExactMessage() {
        String path = "users_missing_comma.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("OnlyOnePiece\n");   // invalid format
            fw.write("M1,M2\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readUsers(path));

        assertEquals("Invalid user line format", ex.getCause().getMessage());
    }

    @Test
    void testReadUsers_WrappedException_ExactMessage() {
        String path = "users_wrapped_error.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("InvalidUserLine\n");  // this triggers parseUser() error
            fw.write("M1,M2\n");
        } catch (Exception ignored) {}

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileReader.readUsers(path));

        // (outer wrapper)
        assertEquals("Error reading users: Invalid user line format", ex.getMessage());

        //  (inner exception)
        assertEquals("Invalid user line format", ex.getCause().getMessage());
    }

}


package WhiteBoxTest.BranceCoverage;
import InOut.FileReader;
import Models.Movie;
import Models.User;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTestBranchCov {

    private final FileReader reader = new FileReader();

    // Helper to create temporary files
    private Path createTempFile(String content) throws Exception {
        Path file = Files.createTempFile("test", ".txt");
        try (FileWriter fw = new FileWriter(file.toFile())) {
            fw.write(content);
        }
        return file;
    }

    @Test
    void testValidMovie() throws Exception {
        assertDoesNotThrow(() -> {
            Path file = createTempFile("Movie1,1\nAction,Drama\n");
            List<Movie> movies = reader.readMovies(file.toString());
            assertEquals(1, movies.size());
            assertEquals("Movie1", movies.get(0).getTitle());
        });
    }

    @Test
    void testMovieLineMissingComma() throws Exception {
        Path file = createTempFile("Movie1 1\nAction\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readMovies(file.toString()));
        assertTrue(ex.getCause().getMessage().contains("Invalid movie line format"));
    }
    @Test
    void testValidUser() throws Exception {
        assertDoesNotThrow(() -> {
            Path file = createTempFile("John,200\nMovie1\n");
            List<User> users = reader.readUsers(file.toString());
            assertEquals(1, users.size());
            assertEquals("John", users.get(0).getName());
        });
    }

    @Test
    void testUserLineMissingComma() throws Exception {
        Path file = createTempFile("John1\nMovie1\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readUsers(file.toString()));
        assertTrue(ex.getCause().getMessage().contains("Invalid user line format"));
    }

    @Test
    void testUserNameStartsWithSpace() throws Exception {
        Path file = createTempFile(" John,1\nMovie1\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readUsers(file.toString()));
        assertTrue(ex.getMessage().contains("ERROR: User Name"));
    }
}


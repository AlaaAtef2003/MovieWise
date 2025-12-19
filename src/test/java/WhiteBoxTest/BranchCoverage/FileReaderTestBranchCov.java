package WhiteBoxTest.BranchCoverage;
import InOut.FileReader;
import Models.Movie;
import Models.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    public void testMovieLineMissingComma() throws Exception {
        Path file = createTempFile("Movie1 1\nAction\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readMovies(file.toString()));
        assertTrue(ex.getCause().getMessage().contains("Invalid movie line format"));
    }

    @Test
    public void testUserLineMissingComma() throws Exception {
        Path file = createTempFile("John1\nMovie1\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readUsers(file.toString()));
        assertTrue(ex.getCause().getMessage().contains("Invalid user line format"));
    }

    @Test
    public void testUserNameStartsWithSpace() throws Exception {
        Path file = createTempFile(" John,1\nMovie1\n");
        Exception ex = assertThrows(RuntimeException.class, () -> reader.readUsers(file.toString()));
        assertTrue(ex.getMessage().contains("ERROR: User Name"));
    }
}


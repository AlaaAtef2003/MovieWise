package BlackBoxTest;

import Integration.Integration;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class StateTransitionTest {

    Integration integration = new Integration();

    // ---------------------------------------------------------
    // Helper Methods
    // ---------------------------------------------------------
    private void writeFile(String name, String content) throws Exception {
        try (FileWriter fw = new FileWriter(name)) {
            fw.write(content);
        }
    }

    private String readFile(String name) throws Exception {
        return Files.readString(Path.of(name));
    }

    // -----------------------------------------------------------
    // T1 – S0 → S4 (Movie file error: invalid movie line format)
    // -----------------------------------------------------------
    @Test
    void ST_MovieFileError_TransitionsToErrorState() throws Exception {

        writeFile("movies.txt",
                "InvalidLine\n" +     // Missing comma → invalid movie format
                        "Action\n");

        writeFile("users.txt",
                "John,12345678A\nM001\n");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("Invalid movie line format", ex.getMessage());
    }

    // -----------------------------------------------------------
    // T2 – S0 → S1 → S4 (User file error: invalid user line format)
    // -----------------------------------------------------------
    @Test
    void ST_UserFileError_TransitionsToErrorState() throws Exception {

        writeFile("movies.txt", "Matrix,M123\nAction\n");

        // 2 lines → forces parsing of user info
        writeFile("users.txt",
                "WrongLine\n" +     // Missing comma → invalid user format
                        "M001\n");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("Invalid user line format", ex.getMessage());
    }

    // -----------------------------------------------------------
    // T3 – S0 → S1 → S2 → S4 (Internal validation error)
    // -----------------------------------------------------------
    @Test
    void ST_InternalProcessingError_TransitionsToErrorState() throws Exception {

        writeFile("movies.txt",
                "Matrix,WRONGID\nAction\n");

        writeFile("users.txt",
                "John,12345678A\nWRONGID\n");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie Id letters WRONGID are wrong", ex.getMessage());
    }

    // -----------------------------------------------------------
    // T4 – S0 → S1 → S2 → S3 → S5 (Successful processing)
    // -----------------------------------------------------------
    @Test
    void ST_SuccessfulProcessing_TransitionsToOutputGenerated() throws Exception {

        writeFile("movies.txt",
                "Matrix,M001\nAction\n" +
                        "John Wick,JW002\nAction\n");

        writeFile("users.txt",
                "Sam,12345678A\nM001\n");

        assertDoesNotThrow(() ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("Sam,12345678A\nMatrix,John Wick\n", readFile("output.txt"));
    }

    // -----------------------------------------------------------
    // T5 – S0 → S4 (Empty Movie File)
    // -----------------------------------------------------------
    @Test
    void ST_EmptyMovieFile_ErrorState() throws Exception {

        writeFile("movies.txt", "");
        writeFile("users.txt", "Sam,12345678A\nM001\n");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie file is empty", ex.getMessage());
    }

    // -----------------------------------------------------------
    // T6 – S0 → S1 → S4 (Empty User File)
    // -----------------------------------------------------------
    @Test
    void ST_EmptyUserFile_ErrorState() throws Exception {

        writeFile("movies.txt", "Matrix,M001\nAction\n");
        writeFile("users.txt", "");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: User file is empty", ex.getMessage());
    }
}
package BlackBoxTest;

import Integration.Integration;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DecisionTableTest {

    Integration integration = new Integration();

    // ---------------- Helper Methods ----------------

    private void writeFile(String file, String content) throws Exception {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
    }

    private String readFile(String file) throws Exception {
        return Files.readString(Path.of(file));
    }



    //ERROR: Movie Title matrix is wrong
    @Test
    void DT_ErrorInMovieTitle() throws Exception {

        writeFile("movies.txt",
                "matrix,M123\n" +    // invalid title (lowercase)
                        "Action\n");

        writeFile("users.txt",
                "John,12345678A\nM123\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie Title matrix is wrong", ex.getMessage());
    }

    @Test
    void DT_MovieTitle_Valid() throws Exception {

        writeFile("movies.txt",
                "Matrix,M123\n" +    // VALID title (capitalized)
                        "Action\n");

        writeFile("users.txt",
                "John,12345678A\nM123\n");

        assertDoesNotThrow(() ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        // Expected output: user line and recommended movies
        String output = readFile("output.txt");

        assertEquals("John,12345678A\nMatrix\n", output);
    }


    //ERROR: Movie ID letters XX123 are wrong
    @Test
    void DT_ErrorInMovieIdLetters() throws Exception {

        writeFile("movies.txt",
                "Matrix,XX123\n" +   // Wrong prefix
                        "Action\n");

        writeFile("users.txt",
                "John,12345678A\nXX123\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie Id letters XX123 are wrong", ex.getMessage());
    }

    //ERROR: Movie Id numbers M123 aren’t unique
    @Test
    void DT_ErrorInMovieIdUniqueNumbers() throws Exception {

        writeFile("movies.txt",
                """
                        Matrix,M123
                        Action
                        Matrix 2,M123
                        Action
                        """);  // Duplicate numeric part 123

        writeFile("users.txt",
                "John,12345678A\nM123\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie Id numbers M123 aren’t unique", ex.getMessage());
    }
    //ERROR: Username  John is wrong
    @Test
    void DT_ErrorInUserName() throws Exception {

        writeFile("movies.txt",
                "Matrix,M123\nAction\n");

        writeFile("users.txt",
                " John,12345678A\nM123\n");  // Leading space → invalid

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: User Name  John is wrong", ex.getMessage());
    }

    @Test
    void DT_ErrorInUserName_Valid() throws Exception {

        // Valid movie file
        writeFile("movies.txt",
                """
                        Matrix,M123
                        Action
                        """);

        // VALID username (no leading space)
        writeFile("users.txt",
                """
                        John,12345678A
                        M123
                        """);

        // Should NOT throw exception
        assertDoesNotThrow(() ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        // Expected output: user + recommended movies
        String output = readFile("output.txt");

        assertEquals(
                "John,12345678A\nMatrix\n",
                output
        );
    }


    // ============================================================
    // R1: INVALID MOVIE FILE FORMAT → EXACT MESSAGE
    // ============================================================

    @Test
    void DT_R1_InvalidMovieFile_ThrowsError() throws Exception {

        writeFile("movies.txt",
                "InvalidLineWithoutComma\nAction\n");

        writeFile("users.txt",
                "John,12345678A\nM001\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        assertEquals("Invalid movie line format", ex.getMessage());

    }
    //ERROR: User ID 12345 is wrong
    @Test
    void DT_ErrorInUserId() throws Exception {

        writeFile("movies.txt",
                "Matrix,M123\nAction\n");

        writeFile("users.txt",
                "John,12345\nM123\n");  // invalid user id

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: User Id 12345 is wrong", ex.getMessage());

    }

    @Test
    void DT_ErrorInUserId_Valid() throws Exception {

        // Valid movie file
        writeFile("movies.txt",
                """
                        Matrix,M123
                        Action
                        """);

        // VALID user id (8 digits + optional letter)
        writeFile("users.txt",
                """
                        John,12345678A
                        M123
                        """);

        // Should NOT throw any exception
        assertDoesNotThrow(() ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        // Expected output (user + recommended movie)
        String output = readFile("output.txt");

        assertEquals(
                "John,12345678A\nMatrix\n",
                output
        );
    }



    // ============================================================
    // R2: INVALID USER FILE FORMAT → EXACT MESSAGE
    // ============================================================
    @Test
    void InvalidUserFile_ThrowsError() throws Exception {

        writeFile("movies.txt", "Matrix,M123\nAction\n");
        writeFile("users.txt", "InvalidUserLine\nM1\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        assertEquals("Invalid user line format", ex.getMessage());

    }


    @Test
    void InvalidInternalData_ThrowsError() throws Exception {

        writeFile("movies.txt", "Matrix,WRONGED\nAction\n");
        writeFile("users.txt", "John,12345678A\nWRONGED\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt")
        );

        assertEquals("ERROR: Movie Id letters WRONGED are wrong", ex.getMessage());
    }

    // ============================================================
    // R4: NO RECOMMENDATIONS → OUTPUT NO EXCEPTION
    // ============================================================
    @Test
    void NoRecommendations_OutputBlank() throws Exception {

        writeFile("movies.txt", "Matrix,M123\nAction\n");
        writeFile("users.txt", "John,12345678A\nUNKNOWN\n");

        integration.outputFile("users.txt", "movies.txt", "output.txt");

        assertEquals("John,12345678A\n\n", readFile("output.txt"));
    }

    @Test
    void Recommendations() throws Exception {

        writeFile("movies.txt",
                """
                        Matrix,M001
                        Action
                        John Wick,JW002
                        Action
                        """);

        writeFile("users.txt",
                "Sam,12345678A\nM001\n");

        integration.outputFile("users.txt", "movies.txt", "output.txt");

        assertEquals("Sam,12345678A\nMatrix,John Wick\n", readFile("output.txt"));
    }

    @Test
    void EmptyMovieFile_ThrowsError() throws Exception {

        writeFile("movies.txt", "");
        writeFile("users.txt", "Sam,12345678A\nM001\n");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: Movie file is empty", ex.getMessage());
    }

    @Test
    void DT_R9_EmptyUserFile_ThrowsError() throws Exception {

        writeFile("movies.txt", "Matrix,M001\nAction\n");
        writeFile("users.txt", "");

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                integration.outputFile("users.txt", "movies.txt", "output.txt"));

        assertEquals("ERROR: User file is empty", ex.getMessage());
    }

    @Test
    void MovieWithNoGenres() throws Exception {

        writeFile("movies.txt", "Matrix,M001\n\n");
        writeFile("users.txt", "Sam,12345678A\nM001\n");

        integration.outputFile("users.txt", "movies.txt", "output.txt");

        assertEquals("Sam,12345678A\nMatrix\n", readFile("output.txt"));
    }


    @Test
    void MultipleUsers() throws Exception {

        writeFile("movies.txt",
                """
                        Matrix,M001
                        Action
                        John Wick,JW002
                        Action
                        """);

        writeFile("users.txt",
                """
                        Sara,10000000A
                        M001
                        Mark,20000000B
                        UNKNOWN
                        """);

        integration.outputFile("users.txt", "movies.txt", "output.txt");

        String expected =
                """
                        Sara,10000000A
                        Matrix,John Wick
                        Mark,20000000B
                        
                        """;

        assertEquals(expected, readFile("output.txt"));
    }

}
package IntegrationTest;

import Integration.Integration;
import InOut.FileReader;
import InOut.FileWriter;
import Models.User;
import Models.Movie;
import Services.RecommendMoviesValidator;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {


    private Path usersFile;
    private Path moviesFile;
    private Path outputFile;

    @BeforeEach
    void setUp() throws IOException {
        usersFile = Files.createTempFile("users", ".txt");
        moviesFile = Files.createTempFile("movies", ".txt");
        outputFile = Files.createTempFile("output", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(usersFile);
        Files.deleteIfExists(moviesFile);
        Files.deleteIfExists(outputFile);
    }

    private static final String MOVIES_CONTENT =
            "Avatar 41,A156\n" +
            "SciFi,Adventure\n" +
            "\n" +
            "John Wick,JW123\n" +
            "Action,Crime\n" +
            "\n" +
            "\n" +
            "The 4 Conjuring 31 ,TC133\n" +
            "Horror,Thriller\n" +
            "\n" +
            "\n" +
            "Iron Man,IM902\n" +
            "Action,SciFi,Animation,Family\n" +
            "\n" +
            "\n" +
            "Frozen,F314\n" +
            "Animation,Family\n";

    private static final String USERS_CONTENT =
            "Ahmed Ali,12345678A\n" +
            "JW123,A567\n" +
            "\n" +
            "sara Mostafa,87654322\n" +
            "TC489,IM902\n" +
            "\n" +
            "Omar Khaled,11223344C\n" +
            "F314\n" +
            "\n" +
            "Mona Youssef,99887766D\n" +
            "JW123,TC489,F314\n";

    private static final String EXPECTED_RECOMMENDATIONS =
            "Ahmed Ali,12345678A\n" +
            "John Wick,Iron Man\n" +
            "sara Mostafa,87654322\n" +
            "Avatar 41,John Wick,Iron Man,Frozen\n" +
            "Omar Khaled,11223344C\n" +
            "Iron Man,Frozen\n" +
            "Mona Youssef,99887766D\n" +
            "John Wick,Iron Man,Frozen\n";

    @Test
    @DisplayName("Bottom-up: FileReader can parse movies and users from given format")
    void bottomUp_fileReaderParsesFiles() throws Exception {
        Files.writeString(moviesFile, MOVIES_CONTENT);
        Files.writeString(usersFile, USERS_CONTENT);

        FileReader reader = new FileReader();

        List<Movie> movies = reader.readMovies(moviesFile.toString());
        List<User> users = reader.readUsers(usersFile.toString());

        assertFalse(movies.isEmpty(), "Movies list should not be empty");
        assertFalse(users.isEmpty(), "Users list should not be empty");

    }

    @Test
    @DisplayName("Bottom-up: FileWriter + RecommendMoviesValidator produce recommendations")
    void bottomUp_fileWriterAndValidatorIntegration() throws Exception {

        FileReader reader = new FileReader();
        FileWriter writer = new FileWriter();
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        Files.writeString(moviesFile, MOVIES_CONTENT);
        Files.writeString(usersFile, USERS_CONTENT);

        List<Movie> movies = reader.readMovies(moviesFile.toString());
        List<User> users = reader.readUsers(usersFile.toString());

        writer.writeRecommendations(outputFile.toString(), users, movies, recommender);

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String actual = Files.readString(outputFile);

        assertNotNull(actual);
        assertFalse(actual.isBlank());

        assertEquals(
                EXPECTED_RECOMMENDATIONS.trim(),
                actual.trim(),
                "Recommendations content should match expected output"
        );
    }

    @Test
    @DisplayName("Bottom-up: Integration.outputFile connects reader, validator, writer end-to-end")
    void bottomUp_integrationOutputFile() throws Exception {
        Files.writeString(moviesFile, MOVIES_CONTENT);
        Files.writeString(usersFile, USERS_CONTENT);

        Integration integration = new Integration();

        integration.outputFile(
                usersFile.toString(),
                moviesFile.toString(),
                outputFile.toString()
        );

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String actual = Files.readString(outputFile);

        assertEquals(
                EXPECTED_RECOMMENDATIONS.trim(),
                actual.trim(),
                "Integration.outputFile should produce the expected recommendations"
        );
    }


    @Test
    @DisplayName("Top-down: empty movies file triggers 'Movie file is empty' error")
    void topDown_emptyMoviesFile() throws Exception {
        Files.writeString(usersFile, USERS_CONTENT);

        Integration integration = new Integration();

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> integration.outputFile(usersFile.toString(),moviesFile.toString(),outputFile.toString())
        );

        assertTrue(ex.getMessage().contains("Movie file is empty"),"Exception message should mention empty movie file");
    }

    @Test
    @DisplayName("Top-down: empty users file triggers 'User file is empty' error")
    void topDown_emptyUsersFile() throws Exception {

        Files.writeString(moviesFile, MOVIES_CONTENT);

        Integration integration = new Integration();

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> integration.outputFile(
                        usersFile.toString(),
                        moviesFile.toString(),
                        outputFile.toString()
                )
        );

        assertTrue(
                ex.getMessage().contains("User file is empty"),
                "Exception message should mention empty user file"
        );
    }

    @Test
    @DisplayName("Top-down: full flow with valid users and movies produces recommendations")
    void topDown_fullValidFlow() throws Exception {
        Files.writeString(moviesFile, MOVIES_CONTENT);
        Files.writeString(usersFile, USERS_CONTENT);

        Integration integration = new Integration();
        integration.outputFile(
                usersFile.toString(),
                moviesFile.toString(),
                outputFile.toString()
        );

        assertTrue(Files.exists(outputFile), "Output file should be created");
        String actual = Files.readString(outputFile);
        assertNotNull(actual);
        assertFalse(actual.isBlank());

        assertEquals(
                EXPECTED_RECOMMENDATIONS.trim(),
                actual.trim(),
                "Top-down full flow should produce expected recommendations"
        );
    }
}

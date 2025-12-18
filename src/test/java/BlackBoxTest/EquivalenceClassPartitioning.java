package BlackBoxTest;

import Integration.Integration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EquivalenceClassPartitioning {

    Integration integeration = new Integration();

    // Helper Methods
    private void writeFile(String file, String content) throws Exception {
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(content);
        }
    }

    private String readFile(String file) throws IOException {
        return Files.readString(Path.of(file));
    }

    private void recommendFile(){
        integeration.outputFile("users.txt", "movies.txt", "recommend_val.txt");
    }

    //Test Cases


    @Test
    void validMovieRecommendation() throws Exception {

        writeFile("movies.txt","Avatar,A456\nAction");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        assertDoesNotThrow(this::recommendFile);
        assertEquals("Ahmed,12345678A\nAvatar\n", readFile("recommend_val.txt"));
    }

    @Test
    public void MovieEmptyfile() throws Exception {
        writeFile("movies.txt","");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie file is empty" , ex.getMessage());
    }

    @Test
    public void UserEmptyFile() throws Exception {
        writeFile("movies.txt","Avatar,A456\nAction");
        writeFile("users.txt", "");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: User file is empty" , ex.getMessage());
    }


    @Test
    public void MovieStartWithSmallLetter() throws Exception {
        writeFile("movies.txt","avatar,A456\nAction");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie Title avatar is wrong" , ex.getMessage());
    }

    @Test
    public void MovieStartWithNumber() throws Exception {
        writeFile("movies.txt","4avatar,A456\n Action");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie Title 4avatar is wrong" , ex.getMessage());
    }


    @Test
    public void MovieWithWrongId() throws Exception {
        writeFile("movies.txt","Avatar,H456\nAction");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie Id letters H456 are wrong" , ex.getMessage());
    }
    @Test
    public void MovieWithoutUniqueId() throws Exception {
        writeFile("movies.txt", """
                Avatar,A456
                Action
                Army,A456
                Action""");
        writeFile("users.txt", "Ahmed,12345678A\nA456");
        RuntimeException ex = assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie Id numbers A456 aren’t unique" , ex.getMessage());
    }


    @Test
    void MovieWithNoGenres() throws Exception {

        writeFile("movies.txt", "Avatar,A456\n\n");
        writeFile("users.txt", "Ahmed,12345678A\nA456\n");

        recommendFile();
        assertEquals("Ahmed,12345678A\nAvatar\n", readFile("recommend_val.txt"));
    }


    @Test
    public void ValidUserName() throws Exception {
        writeFile("movies.txt", "Avatar,A456\nAction\n");
        writeFile("users.txt" , "Ahmed,12345678A\nA456");
        assertDoesNotThrow(this::recommendFile);

    }

    @Test
    public void WrongUserName() throws Exception {
        writeFile("movies.txt", "Avatar,A456\nAction\n");
        writeFile("users.txt" , "A7med,12345678A\nA456");
        RuntimeException ex =assertThrows( RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: User Name A7med is wrong" ,ex.getMessage());

    }
    @Test
    public void UserWithSpaceAtFirst() throws Exception {
        writeFile("movies.txt", "Avatar,A456\nAction\n");
        writeFile("users.txt" , " Ahmed,12345678A\nA456");
        RuntimeException ex =assertThrows( RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: User Name  Ahmed is wrong" ,ex.getMessage());

    }


    @Test
    public void UserWithWrongID() throws Exception {
        writeFile("movies.txt", "Avatar,A456\nAction\n");
        writeFile("users.txt" , "Ahmed,1234567855\nA456");
        RuntimeException ex =assertThrows( RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: User Id 1234567855 is wrong" ,ex.getMessage());
    }

    @Test
    public void MovieWithWrongOrder() throws Exception{
        writeFile("movies.txt", "Avatar,Action\nA456\n");
        writeFile("users.txt" , "Ahmed,12345678A\nA456 ");
        RuntimeException ex =assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: Movie Id letters Action are wrong" , ex.getMessage());

    }

    @Test
    public void UserWithWrongOrder() throws Exception{
        writeFile("movies.txt", "Avatar,A456\nAction\n");
        writeFile("users.txt" , "Ahmed,A456\n12345678A");
        RuntimeException ex =assertThrows(RuntimeException.class , this::recommendFile);
        assertEquals("ERROR: User Id A456 is wrong" , ex.getMessage());

    }




}
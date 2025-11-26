package UnitTest;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import InOut.FileReaderService;
import InOut.FileWriterService;
import Models.Movies;
import Models.Users;
import Services.recommendMovies;

class RecommendMovieTest {
    static FileReaderService fs;
    static FileWriter MovieFile,UserFile;
    FileWriterService fws;
    recommendMovies rm;
    //generated file names
    private final String films="MovieFileAmrTest.txt";
    private final String UsersFiles="UsersFileAmrTest.txt";
    private final String recommendFiles="RecommenedFileAmrTest.txt";


    @BeforeEach
    public void setup() throws Exception {
        fs = new FileReaderService();
        fws = new FileWriterService();
        rm = new recommendMovies();
        MovieFile = new FileWriter(films, false);
        UserFile = new FileWriter(UsersFiles, false);
    }
    @Test

    void testMoviesHappyScenario() throws Exception {
        MovieFile.write("""
                Avatar,A156\r
                SciFi,Adventure\r
                \r
                John Wick,JW123\r
                Action,Crime\r
                \r
                \r
                The Conjuring,TC133\r
                Horror,Thriller\r
                \r
                \r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                \r
                \r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                \r
                sara Mostafa,87654322\r
                TC489,IM902\r
                \r
                Omar Khaled,11223344\r
                F314\r
                \r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.close();
        UserFile.close();
        List<Movies> moviesInput = fs.readMovies(films);
        List<Users> usersInput = fs.readUsers(UsersFiles);
        fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));

        assertEquals("John Wick,Iron Man", lines.get(1));
        assertEquals("Avatar,John Wick,Iron Man,Frozen", lines.get(3));
        assertEquals("Iron Man,Frozen", lines.get(5));
        assertEquals("John Wick,Iron Man,Frozen", lines.get(7));




    }



    @Test

    void testMoviesNegativeScenario2DigitsId() throws Exception {
        //two digits id
        MovieFile.write("""
                Avatar,A15\r
                SciFi,Adventure\r
                \r
                John Wick,JW123\r
                Action,Crime\r
                The Conjuring,TC133\r
                Horror,Thriller\r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();
//		Exception ex = assertThrows(Exception.class, () -> {
//		    fs.readMovies(films);
//		});
//		assertEquals("ERROR: Movie Id letters A15 are wrong",ex.getMessage());
        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("ERROR: Movie Id letters A15 are wrong",lines.getFirst());




    }





    @Test

    void testMoviesNegativeScenario4digitsId() throws Exception {
        //4 digits id
        MovieFile.write("""
                Avatar,A158\r
                SciFi,Adventure\r
                John Wick,JW123\r
                Action,Crime\r
                The Conjuring,TC1335\r
                Horror,Thriller\r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();
//		Exception ex = assertThrows(Exception.class, () -> {
//		    fs.readMovies(films);
//		});
//		assertEquals("ERROR: Movie Id letters A15 are wrong",ex.getMessage());
        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("ERROR: Movie Id letters TC1335 are wrong",lines.getFirst());




    }




    @Test

    void testMoviesNegativeScenarioNoComma() throws Exception {
        //removed the comma
        MovieFile.write("""
                Avatar,A158\r
                SciFi,Adventure\r
                John Wick,JW123\r
                Action,Crime\r
                The Conjuring,TC133\r
                Horror,Thriller\r
                Iron ManIM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();

        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("Invalid movie line format",lines.getFirst());//bug:it's not line 1  it's 7

    }


    @Test

    void testMoviesNegativeScenarioNotUniqueMovieId() throws Exception {
        //not unique id numbers
        MovieFile.write("""
                Avatar,A158\r
                SciFi,Adventure\r
                John Wick,JW158\r
                Action,Crime\r
                The Conjuring,TC133\r
                Horror,Thriller\r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();

        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("ERROR: Movie Id numbers JW158 aren’t unique",lines.getFirst());//bug:it's not line 1  it's 6



    }

    @Test

    void testMoviesNegativeScenarioWrongMovieIDLetters() throws Exception {
        //wrong id letters
        MovieFile.write("""
                Avatar,A158\r
                SciFi,Adventure\r
                John Wick,PW151\r
                Action,Crime\r
                The Conjuring,TC133\r
                Horror,Thriller\r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();
//		Exception ex = assertThrows(Exception.class, () -> {
//		    fs.readMovies(films);
//		});
//		assertEquals("ERROR: Movie Id letters A15 are wrong",ex.getMessage());
        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("ERROR: Movie Id letters PW151 are wrong",lines.get(0));//bug:it's not line 1  it's 6



    }


    @Test

    void testMoviesNegativeScenarioWrongTitle() throws Exception {
        //wrong movie title format
        MovieFile.write("""
                Avatar,A158\r
                SciFi,Adventure\r
                john wick,JW151\r
                Action,Crime\r
                The Conjuring,TC133\r
                Horror,Thriller\r
                Iron Man,IM902\r
                Action,SciFi,Animation,Family\r
                Frozen,F314\r
                Animation,Family\r
                """);


        UserFile.write("""
                Ahmed Ali,12345678\r
                JW123,A567\r
                sara Mostafa,87654322\r
                TC489,IM902\r
                Omar Khaled,11223344\r
                F314\r
                Mona Youssef,99887766\r
                JW123,TC489,F314\r
                """);
        MovieFile.flush();
        MovieFile.close();
        UserFile.flush();
        UserFile.close();
//		Exception ex = assertThrows(Exception.class, () -> {
//		    fs.readMovies(films);
//		});
//		assertEquals("ERROR: Movie Id letters A15 are wrong",ex.getMessage());
        try {
            List<Movies> moviesInput = fs.readMovies(films);
            List<Users> usersInput = fs.readUsers(UsersFiles);
            fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);

        }
        catch (Exception e) {

            try {
                FileWriter writer = new FileWriter(recommendFiles);
                writer.write(e.getMessage());
                writer.close();
            } catch (Exception ex) {
                //couldn't write the error in the file
            }

        }
        List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
        assertEquals(1, lines.size());
        assertEquals("ERROR: Movie Title john wick is wrong",lines.get(0));//bug:it's not line 1  it's 6



    }





    @AfterEach
    public void cleanup() throws IOException {
        // Close files if not already closed
        if (MovieFile != null) {
            MovieFile.close();
        }
        if (UserFile != null) {
            UserFile.close();
        }

        // Delete test files if they exist
        String[] filesToDelete = { films, UsersFiles, recommendFiles };
        for (String fileName : filesToDelete) {
            File file = new File(fileName);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("Warning: could not delete file " + fileName);
                }
            }
        }
    }



}
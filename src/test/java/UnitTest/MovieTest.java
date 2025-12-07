package UnitTest;


import Models.Movie;
import Services.InputValidator;
import org.junit.Before;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.*;

public class MovieTest {
    private InputValidator validator;
    private Set<String> ID;

    @Before
    public void setup() {
        validator = new InputValidator();
        ID = new HashSet<>();
    }


    // =========================== Test the Name of the Movie ===========================//


    @Test
    public void testValidMovie() {
        Movie movie = new Movie("John Wick", "JW123", Arrays.asList("Action","Crime"));
        validator.validateMovie(movie, ID);
    }


    // Check first letter is small
    @Test
    public void TestFirstLetterWithLowerCase(){
        Movie movie = new Movie("avatar" ,"A378" , Arrays.asList("Adventure" ,"Fantasy"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // Space at first
    @Test
    public void TestSpaceAtFirst(){
        Movie movie = new Movie(" Avatar" ,"A378" , Arrays.asList("Adventure" ,"Fantasy"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //Check spaces at last
    @Test
    public void TestSpacesAtLast(){
        Movie movie = new Movie("John Wick " ,"JW378" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie, ID);
    }

    //check Capital letter after space
    @Test
    public void TestCapitalAfterSpace(){
        Movie movie = new Movie("John wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //Start with number
    @Test
    public void TestStartWithNumber(){
        Movie movie = new Movie("4John Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //    End with number
    @Test
    public void TestEndWithNumber(){
        Movie movie = new Movie("John Wick 4" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie,ID);
    }

    // second word start with number
    @Test
    public void TestSecondStartWithNumber(){
        Movie movie = new Movie("John 5Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // dash between the words
    @Test
    public void TestDashBetweenWords(){
        Movie movie = new Movie("John-Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // movie with more than two words
    @Test
    public void TestMoreThanTwoWords(){
        Movie movie = new Movie("Harry Potter Magic" ,"HPM148" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie, ID);
    }

    // ================================== Test ID Scenario ==================================================//

    //check if the ID start with number
    @Test
    public void TestIdStartWithNumber() {
        Movie movie = new Movie("Frozen", "920", Arrays.asList("Family", "Animation"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //the id start with small letter
    @Test
    public void TestIdStartWithSmallLetter(){
        Movie movie = new Movie("Frozen" ,"f920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //the id with letter in the last
    @Test
    public void TestIdWithLetterInLast(){
        Movie movie = new Movie("Frozen" ,"920F" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // start with more than letter
    @Test
    public void TestIdWithMoreLetters(){
        Movie movie = new Movie("Frozen" ,"FR920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // id with more than 3 numbers
    @Test
    public void TestIdWithMoreNumbers(){
        Movie movie = new Movie("Frozen" ,"F9201" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }
    // id with more than 3 numbers
    @Test
    public void TestIdWithLessNumbers(){
        Movie movie = new Movie("Frozen" ,"F92" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // check id of two words
    @Test
    public void TestIdWithTwoWords(){
        Movie movie = new Movie("John Wick" ,"JW578" , Arrays.asList("Action","Crime"));
        validator.validateMovie(movie ,ID);
    }

    //the id letters is wrong
    @Test
    public void TestIdWrongOrder(){
        Movie movie = new Movie("John Wick" ,"J57W8" , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //The id Start With Space
    @Test
    public void TestIdStartWithSpace(){
        Movie movie = new Movie("John Wick" ," JW578" , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //The id end With Space
    @Test
    public void TestIdEndWithSpace(){
        Movie movie = new Movie("John Wick" ,"JW578 " , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }


    //Check id is unique
    @Test
    public void testDuplicateMovieId() {
        // First movie (valid)
        Movie m1 = new Movie("Avatar", "A123", List.of("Adventure"));
        validator.validateMovie(m1, ID);

        // Second movie with same numeric ID → ERROR
        Movie m2 = new Movie("Avengers", "A123", List.of("Action"));
        assertThrows(RuntimeException.class, () ->
                validator.validateMovie(m2, ID)
        );
    }

    // the name is all capital
    @Test
    public void TestAllCapitalName(){
        Movie movie = new Movie("FROZEN" ,"F920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class ,()->validator.validateMovie(movie,ID));
    }

    // test the order
    @Test
    public void testValidMovieCreation() {
        Movie movie = new Movie("Avatar", "A234", Arrays.asList("Action", "Adventure"));

        assertEquals("Avatar", movie.getTitle());
        assertEquals("A234",movie.getMovieId());
        assertEquals(Arrays.asList("Action", "Adventure") , movie.getGenres());
    }

}
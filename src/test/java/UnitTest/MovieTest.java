package UnitTest;


import Models.Movies;
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
        Movies movie = new Movies("John Wick", "JW123", Arrays.asList("Action","Crime"));
        validator.validateMovie(movie, ID);
    }


    // Check first letter is small
    @Test
    public void TestFirstLetterWithLowerCase(){
        Movies movie = new Movies("avatar" ,"A378" , Arrays.asList("Adventure" ,"Fantasy"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // Space at first
    @Test
    public void TestSpaceAtFirst(){
        Movies movie = new Movies(" Avatar" ,"A378" , Arrays.asList("Adventure" ,"Fantasy"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //Check spaces at last
    @Test
    public void TestSpacesAtLast(){
        Movies movie = new Movies("John Wick " ,"JW378" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie, ID);
    }

    //check Capital letter after space
    @Test
    public void TestCapitalAfterSpace(){
        Movies movie = new Movies("John wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //Start with number
    @Test
    public void TestStartWithNumber(){
        Movies movie = new Movies("4John Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //    End with number
    @Test
    public void TestEndWithNumber(){
        Movies movie = new Movies("John Wick 4" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie,ID);
    }

    // second word start with number
    @Test
    public void TestSecondStartWithNumber(){
        Movies movie = new Movies("John 5Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // dash between the words
    @Test
    public void TestDashBetweenWords(){
        Movies movie = new Movies("John-Wick" ,"JW123" , Arrays.asList("Action" ,"Crime"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    // movie with more than two words
    @Test
    public void TestMoreThanTwoWords(){
        Movies movie = new Movies("Harry Potter Magic" ,"HPM148" , Arrays.asList("Action" ,"Crime"));
        validator.validateMovie(movie, ID);
    }

    // ================================== Test ID Scenario ==================================================//

    //check if the ID start with number
    @Test
    public void TestIdStartWithNumber() {
        Movies movie = new Movies("Frozen", "920", Arrays.asList("Family", "Animation"));
        assertThrows(RuntimeException.class, () -> validator.validateMovie(movie, ID));
    }

    //the id start with small letter
    @Test
    public void TestIdStartWithSmallLetter(){
        Movies movie = new Movies("Frozen" ,"f920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //the id with letter in the last
    @Test
    public void TestIdWithLetterInLast(){
        Movies movie = new Movies("Frozen" ,"920F" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // start with more than letter
    @Test
    public void TestIdWithMoreLetters(){
        Movies movie = new Movies("Frozen" ,"FR920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // id with more than 3 numbers
    @Test
    public void TestIdWithMoreNumbers(){
        Movies movie = new Movies("Frozen" ,"F9201" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }
    // id with more than 3 numbers
    @Test
    public void TestIdWithLessNumbers(){
        Movies movie = new Movies("Frozen" ,"F92" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    // check id of two words
    @Test
    public void TestIdWithTwoWords(){
        Movies movie = new Movies("John Wick" ,"JW578" , Arrays.asList("Action","Crime"));
        validator.validateMovie(movie ,ID);
    }

    //the id letters is wrong
    @Test
    public void TestIdWrongOrder(){
        Movies movie = new Movies("John Wick" ,"J57W8" , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //The id Start With Space
    @Test
    public void TestIdStartWithSpace(){
        Movies movie = new Movies("John Wick" ," JW578" , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }

    //The id end With Space
    @Test
    public void TestIdEndWithSpace(){
        Movies movie = new Movies("John Wick" ,"JW578 " , Arrays.asList("Action","Crime"));
        assertThrows(RuntimeException.class , ()->validator.validateMovie(movie ,ID));
    }


    //Check id is unique
    @Test
    public void testDuplicateMovieId() {
        // First movie (valid)
        Movies m1 = new Movies("Avatar", "A123", List.of("Adventure"));
        validator.validateMovie(m1, ID);

        // Second movie with same numeric ID → ERROR
        Movies m2 = new Movies("Avengers", "A123", List.of("Action"));
        assertThrows(RuntimeException.class, () ->
                validator.validateMovie(m2, ID)
        );
    }

    // the name is all capital
    @Test
    public void TestAllCapitalName(){
        Movies movie = new Movies("FROZEN" ,"F920" , Arrays.asList("Family" ,"Animation"));
        assertThrows(RuntimeException.class ,()->validator.validateMovie(movie,ID));
    }

    // test the order
    @Test
    public void testValidMovieCreation() {
        Movies movie = new Movies("Avatar", "A234", Arrays.asList("Action", "Adventure"));

        assertEquals("Avatar", movie.getTitle());
        assertEquals("A234",movie.getMovieId());
        assertEquals(Arrays.asList("Action", "Adventure") , movie.getGenres());
    }

}
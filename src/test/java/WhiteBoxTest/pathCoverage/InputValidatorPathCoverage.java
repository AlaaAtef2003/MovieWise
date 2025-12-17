package WhiteBoxTest.pathCoverage;

import static org.junit.jupiter.api.Assertions.*;
import Models.Movie;
import Models.User;
import Services.InputValidator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


class InputValidatorPathCoverage {
	  InputValidator validator = new InputValidator();
	 @Test
	    void testValidateMovie_Valid() {
	        Movie m = new Movie("Matrix Reloaded", "MR123", List.of("Action"));
	        Set<String> usedIds = new HashSet<>();

	        assertDoesNotThrow(() -> validator.validateMovie(m, usedIds));
	    }

	    

	    @Test
	    void testValidateMovieTitle_Invalid() {
	        Movie m = new Movie("mohammed ramadan", "MR123", List.of("Action"));
	        Set<String> usedIds = new HashSet<>();

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Title "+ m.getTitle() +" is wrong", ex.getMessage());
	    }
	    
	    
	    @Test
	    void testValidateMovieIdLetter_Invalid() {
	        Movie m = new Movie("Matrix Reloaded", "mr123", List.of("Action"));
	        Set<String> usedIds = new HashSet<>();

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Id letters " + m.getMovieId() + " are wrong", ex.getMessage());
	    }
	    
	    @Test
	    void testValidateMovieIdLetterAreUnique_Invalid() {
	        Movie m = new Movie("Matrix Reloaded", "mr123", List.of("Action"));
	        Set<String> usedIds = new HashSet<>();

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Id letters " + m.getMovieId() + " are wrong", ex.getMessage());
	    }
	    
	    //more tests
	    @Test
	    void testValidateMovieIdLetters_InvalidPrefix() {
	        Movie m = new Movie("Matrix", "X123", List.of("Action"));
	        Set<String> usedIds = new HashSet<>();

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Id letters " +m.getMovieId()+" are wrong", ex.getMessage());
	    }
	    
	    
	    @Test
	    void testValidateMovieIdLetters_InvalidLength() {
	        Movie m = new Movie("Matrix", "M1", List.of("Action")); // missing required digits
	        Set<String> usedIds = new HashSet<>();

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Id letters "+m.getMovieId()+" are wrong", ex.getMessage());
	    }
	    
	    
	    @Test
	    void testEnsureMovieIdIsUnique_Duplicate() {
	        Set<String> usedIds = new HashSet<>();
	        usedIds.add("123");  // stored number portion

	        Movie m = new Movie("Matrix", "M123", List.of("Action"));

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> validator.validateMovie(m, usedIds));

	        assertEquals("ERROR: Movie Id numbers "+m.getMovieId()+" aren’t unique", ex.getMessage());
	    }
	    //users
	    
	    @Test
	    void testValidateUser_Valid()
	    {
	    	User u =new User("Amr","12345678A", null);
	        Set<String> usedIds = new HashSet<>();
	        assertDoesNotThrow(()-> validator.validateUser(u, usedIds));
	    }
	    
	    @Test
	    void testValidateUser_InValidUserNameStartingWithSpace()
	    {
	    	User u =new User(" Amr","12345678A", null);
	        Set<String> usedIds = new HashSet<>();
	        RuntimeException ex =assertThrows(RuntimeException.class,()-> validator.validateUser(u, usedIds));
	        assertEquals(ex.getMessage(), "ERROR: User Name " + u.getName() + " is wrong");
	    }
	    @Test
	    void testValidateUser_InValidUserNameContainsSpecialCharacter()
	    {
	    	User u =new User("Am$","12345678A", null);
	        Set<String> usedIds = new HashSet<>();
	        RuntimeException ex =assertThrows(RuntimeException.class,()-> validator.validateUser(u, usedIds));
	        assertEquals(ex.getMessage(), "ERROR: User Name " + u.getName() + " is wrong");
	    }
	    
	    
	    @Test
	    void testValidateUser_InValidIdFormat()
	    {
	    	User u =new User("Amr","1235678A", null);
	        Set<String> usedIds = new HashSet<>();
	        RuntimeException ex =assertThrows(RuntimeException.class,()-> validator.validateUser(u, usedIds));
	        assertEquals(ex.getMessage(), "ERROR: User Id " + u.getUserId() + " is wrong");
	    }
	    
	    
	    @Test
	    void testValidateUser_IdNotUnique()
	    {
	    	 Set<String> usedIds = new HashSet<>();
	    	 usedIds.add("12345678B");
	    	User u2=new User("Ahmed","12345678B", null);
	        RuntimeException ex =assertThrows(RuntimeException.class,()-> validator.validateUser(u2, usedIds));
	        assertEquals(ex.getMessage(), "ERROR: User Id " + u2.getUserId() + " is wrong");
	    }


	


	 

	   
}

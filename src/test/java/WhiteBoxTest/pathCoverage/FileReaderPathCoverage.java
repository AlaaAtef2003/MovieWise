package WhiteBoxTest.pathCoverage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import InOut.FileReader;
import Models.Movie;
import Models.User;

class FileReaderPathCoverage {
	
    private final String films="MovieFileAmrTest.txt";
    private final String UsersFiles="UsersFileAmrTest.txt";
	FileReader fileReader = new FileReader();
	@Test
	void testReadMovies_FirstLineIsNull() throws Exception {
        String path = films;
        try (FileWriter fw = new FileWriter(path)) {
            
        }
        List<Movie> movies = fileReader.readMovies(path);
        assertEquals(0, movies.size());
    }
	
	@Test
	void testReadMovies_OneMovieIsReadSucessfully() throws Exception {
        String path = films;
        try (FileWriter fw = new FileWriter(path)) {
        	fw.write("Matrix,M123\n");
        	fw.write("Action,SciFi\n");
        }
        List<Movie> movies = fileReader.readMovies(path);
        assertEquals(1, movies.size());
    }
	
	
	
	 @Test
	    void testReadMovies_EmptyLineSkipped_ValidMovie() throws Exception {
	        String path =films;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write("\n");
	            fw.write("Matrix,M123\n");
	            fw.write("Action,SciFi\n");
	        }
	        List<Movie> movies = fileReader.readMovies(path);
	        assertEquals(1, movies.size());
	    }

	 
	 @Test
	    void testReadMovies_GenereLineIsNull() throws Exception {
	        String path = films;
	        try (FileWriter fw = new FileWriter(path)) {
	           
	            fw.write("Matrix,M123\n");
	            ;
	        }
	        List<Movie> movies = fileReader.readMovies(path);
	        assertEquals(0, movies.size());
	    }
	 
	 
	 
	 //Test MovieParse
	 
	 @Test
	   void testParseMovie_Invalid_NoComma_ExactMessage() {
	        String path = films;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write("WrongFormat\n");
	            fw.write("Drama\n");
	        } catch (Exception ignored) {}

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> fileReader.readMovies(path));

	        assertEquals("Invalid movie line format", ex.getCause().getMessage());
	    }
	   
	 @Test
	   void testParseMovie_ValidFormat() throws Exception {
	        String path = films;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write("Matrix,M123\n");
	            fw.write("Action,SciFi\n");
	        } catch (Exception ignored) {}

	        List<Movie> movies = fileReader.readMovies(path);
	        assertEquals(1, movies.size());
	    }
	 
	 
	 
	 //  readUser test
	 
	 @Test
		void testReadUser_FirstLineIsNull() throws Exception {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {

	        }
	        List<User> Users = fileReader.readUsers(path);
	        assertEquals(0, Users.size());
	    }
	 
	 
	 
	 @Test
		void testReadUsers_OneUserIsReadSucessfully() throws Exception {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	        	 fw.write("Ahmed Hassan, 12345678A\n");
		            fw.write("TDK123, I456\n");
	        }
	        List<User> Users = fileReader.readUsers(path);
	        assertEquals(1, Users.size());
	    }
	 
	 
	 @Test
	    void testReadUsers_WrappedException_ExactMessage() {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write("InvalidUserLine\n");  // this triggers parseUser() error
	            fw.write("M1,M2\n");
	        } catch (Exception ignored) {}

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> fileReader.readUsers(path));

	        // (outer wrapper)
	        assertEquals("Invalid user line format", ex.getMessage());

	        //  (inner exception)
	        assertEquals("Invalid user line format", ex.getCause().getMessage());
	    }
	 
	 
	 @Test
	    void testReadUser_EmptyLineSkipped_ValidUser() throws Exception {
		    String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	        	 fw.write("\n");
	        	 fw.write("Ahmed Hassan, 12345678A\n");
		            fw.write("TDK123, I456\n");
	        }
	        List<User> Users = fileReader.readUsers(path);
	        assertEquals(1, Users.size());
	    }
	 
	 @Test
	    void testReadUser_LikedMoviesDoesNotExist() throws Exception {
		    String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	        	 fw.write("Ahmed Hassan, 12345678A\n");
	        }
	        List<User> Users = fileReader.readUsers(path);
	        assertEquals(0, Users.size());
	    }
	 
	  @Test
	    void testParseUser_LeadingSpace_ExactMessage() {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write(" John,12345678A\n");  // Leading space triggers error
	            fw.write("M1,M2\n");
	        } catch (Exception ignored) {}

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> fileReader.readUsers(path));

	        assertEquals("ERROR: User Name  John is wrong", ex.getCause().getMessage());
	    }
	  
	  
	  @Test
	    void testParseUser_MissingComma_ExactMessage() {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	            fw.write("OnlyOnePiece\n");   // invalid format
	            fw.write("M1,M2\n");
	        } catch (Exception ignored) {}

	        RuntimeException ex = assertThrows(RuntimeException.class,
	                () -> fileReader.readUsers(path));

	        assertEquals("Invalid user line format", ex.getCause().getMessage());
	    }
	  
	  
	  @Test
	    void testParseUser_HappyScenario() throws Exception {
	        String path = UsersFiles;
	        try (FileWriter fw = new FileWriter(path)) {
	        	 fw.write("Ahmed Hassan, 12345678A\n");
		            fw.write("TDK123, I456\n");
	        } catch (Exception ignored) {}

	        List<User> Users = fileReader.readUsers(path);
	        assertEquals(1, Users.size());
	    }
	  
	  
	  @AfterEach
	    public void cleanup() throws IOException {
	    
	        // Delete test files if they exist
	        String[] filesToDelete = { films, UsersFiles};
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

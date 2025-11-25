package UnitTest;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import InOut.FileReaderService;
import InOut.FileWriterService;
import Models.Movies;
import Models.Users;
import Services.recommendMovies;

class MovieTest {
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
	    MovieFile = new FileWriter("MovieFileAmrTest.txt", false);
	    UserFile = new FileWriter("UsersFileAmrTest.txt", false);
	}
	@Test 
	
	void testMoviesHappyScenario() throws Exception {
	    MovieFile.write("Avatar,A156\r\n"
	            + "SciFi,Adventure\r\n"
	            + "\r\n"
	            + "John Wick,JW123\r\n"
	            + "Action,Crime\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            + "\r\n"
	            + "\r\n"
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
		MovieFile.close();
		UserFile.close();
	    List<Movies> moviesInput = fs.readMovies(films);
	    List<Users> usersInput = fs.readUsers(UsersFiles);
	    fws.writeRecommendations(recommendFiles, usersInput, moviesInput, rm);	    
	    List<String> lines = Files.readAllLines(Paths.get(recommendFiles));
	    
	    assertEquals(lines.get(1), "John Wick,Iron Man");
	    assertEquals(lines.get(3), "Avatar,John Wick,Iron Man,Frozen");
	    assertEquals(lines.get(5), "Iron Man,Frozen");
	    assertEquals(lines.get(7), "John Wick,Iron Man,Frozen");
	    
	    
	    
	 
	}
	
	

	@Test
	
	void testMoviesNegativeScenario2DigitsId() throws Exception {
	    MovieFile.write("Avatar,A15\r\n"     //two digits id 
	            + "SciFi,Adventure\r\n"
	            + "\r\n"
	            + "John Wick,JW123\r\n"
	            + "Action,Crime\r\n"
	            
	            
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            
	            
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            
	            
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("ERROR: Movie Id letters A15 are wrong",lines.get(0));
	    
	    
	    
	 
	}
	
	
	
	
	
	@Test
	
	void testMoviesNegativeScenario4digitsId() throws Exception {
	    MovieFile.write("Avatar,A158\r\n"      
	            + "SciFi,Adventure\r\n"
	            
	            + "John Wick,JW123\r\n"
	            + "Action,Crime\r\n"
	            
	            
	            + "The Conjuring,TC1335\r\n"//4 digits id
	            + "Horror,Thriller\r\n"
	            
	            
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            
	            
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("ERROR: Movie Id letters TC1335 are wrong",lines.get(0));
	    
	    
	    
	 
	}
	
	
	
	
	@Test
	
	void testMoviesNegativeScenarioNoComma() throws Exception {
	    MovieFile.write("Avatar,A158\r\n"      
	            + "SciFi,Adventure\r\n"
	            + "John Wick,JW123\r\n"
	            + "Action,Crime\r\n"
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            + "Iron ManIM902\r\n"//removed the comma
	            + "Action,SciFi,Animation,Family\r\n"
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("Invalid movie line format",lines.get(0));//bug:it's not line 1  it's 7

	}
	
	
	@Test
	
	void testMoviesNegativeScenarioNotUniqueMovieId() throws Exception {
	    MovieFile.write("Avatar,A158\r\n"      
	            + "SciFi,Adventure\r\n"
	            + "John Wick,JW158\r\n"  //not unique id numbers
	            + "Action,Crime\r\n"
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("ERROR: Movie Id numbers JW158 aren’t unique",lines.get(0));//bug:it's not line 1  it's 6
	    
	    
	 
	}
	
	@Test
	
	void testMoviesNegativeScenarioWrongMovieIDLetters() throws Exception {
	    MovieFile.write("Avatar,A158\r\n"      
	            + "SciFi,Adventure\r\n"
	            + "John Wick,PW151\r\n"  //wrong id letters
	            + "Action,Crime\r\n"
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("ERROR: Movie Id letters PW151 are wrong",lines.get(0));//bug:it's not line 1  it's 6
	    
	    
	 
	}
	
	
	@Test
	
	void testMoviesNegativeScenarioWrongTitle() throws Exception {
	    MovieFile.write("Avatar,A158\r\n"      
	            + "SciFi,Adventure\r\n"
	            + "john wick,JW151\r\n"  //wrong movie title format
	            + "Action,Crime\r\n"
	            + "The Conjuring,TC133\r\n"
	            + "Horror,Thriller\r\n"
	            + "Iron Man,IM902\r\n"
	            + "Action,SciFi,Animation,Family\r\n"
	            + "Frozen,F314\r\n"
	            + "Animation,Family\r\n"
	            + "");

	    
	    UserFile.write("Ahmed Ali,12345678\r\n"
	            + "JW123,A567\r\n"
	            + "sara Mostafa,87654322\r\n"
	            + "TC489,IM902\r\n"
	            + "Omar Khaled,11223344\r\n"
	            + "F314\r\n"
	            + "Mona Youssef,99887766\r\n"
	            + "JW123,TC489,F314\r\n"
	            + "");
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
		 assertEquals(lines.size(), 1);
		assertEquals("ERROR: Movie Title john wick is wrong",lines.get(0));//bug:it's not line 1  it's 6
	    
	    
	 
	}
	
	
	
	//@Test
	//void test2() throws Exception 
	//{
//		MovieFile.write("m.");
//		MovieFile.write("new");
//		MovieFile.close();
//		
//
//		   List<Users>ls = fs.readUsers("F:\\college\\Github\\MovieWise\\recommendations.txt");
//		String hamadaid= ls.get(0).getUserId();
//		assertEquals(hamadaid, "12345678");

	//}
	
	
	
	
	
	
	
//	@Test
//	void testRecommendationOutput(@TempDir Path tempDir) throws Exception {
//
//	    // 1️⃣ Create test movie file
//	    Path movieFile = tempDir.resolve("movies.txt");
//	    Files.writeString(movieFile,
//	            "Avatar,A156\r\n"
//	            + "SciFi,Adventure\r\n"
//	            + "\r\n"
//	            + "John Wick,JW123\r\n"
//	            + "Action,Crime\r\n"
//	            + "\r\n"
//	            + "\r\n"
//	            + "The Conjuring,TC133\r\n"
//	            + "Horror,Thriller\r\n"
//	            + "\r\n"
//	            + "\r\n"
//	            + "Iron Man,IM902\r\n"
//	            + "Action,SciFi,Animation,Family\r\n"
//	            + "\r\n"
//	            + "\r\n"
//	            + "Frozen,F314\r\n"
//	            + "Animation,Family\r\n"
//	            + ""
//	    );
//
//	    // 2️⃣ Create test users file
//	    Path userFile = tempDir.resolve("users.txt");
//	    Files.writeString(userFile,
//		"Ahmed Ali,12345678\r\n"
//		+ "JW123,A567\r\n"
//		+ "\r\n"
//		+ "sara Mostafa,87654322\r\n"
//		+ "TC489,IM902\r\n"
//		+ "\r\n"
//		+ "Omar Khaled,11223344\r\n"
//		+ "F314\r\n"
//		+ "\r\n"
//		+ "Mona Youssef,99887766\r\n"
//		+ "JW123,TC489,F314\r\n"
//		+ ""
//	    );
//
//	    // 3️⃣ Read input using your FileReaderService
//	    FileReaderService fs = new FileReaderService();
//	    List<Movies> movies = fs.readMovies(movieFile.toString());
//	    List<Users> users = fs.readUsers(userFile.toString());
//
//	    // 4️⃣ Output file path
//	    Path recommendationFile = tempDir.resolve("recommendation.txt");
//
//	    // 5️⃣ Call your service
//	    FileWriterService fws = new FileWriterService();
//	    recommendMovies rm = new recommendMovies();
//	    fws.writeRecommendations(
//	            recommendationFile.toString(),
//	            users,
//	            movies,
//	            rm
//	    );
//	    
//	    String output = Files.readString(recommendationFile);
//	    String expected ="Ahmed Ali,12345678\r\n"
//	    		+ "John Wick,Iron Man\r\n"
//	    		+ "sara Mostafa,87654322\r\n"
//	    		+ "Avatar,John Wick,Iron Man,Frozen\r\n"
//	    		+ "Omar Khaled,11223344\r\n"
//	    		+ "Iron Man,Frozen\r\n"
//	    		+ "Mona Youssef,99887766\r\n"
//	    		+ "John Wick,Iron Man,Frozen\r\n"
//	    		+ "";
//	    assertEquals(expected.replace("\r\n", "\n").trim(),
//	    	    output.replace("\r\n", "\n").trim());
//	}
//	
//	
//	
	
	
	@AfterEach
	public void end() throws IOException
	{

	}



}

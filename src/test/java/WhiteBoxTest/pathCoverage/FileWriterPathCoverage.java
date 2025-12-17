package WhiteBoxTest.pathCoverage;



import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Models.Movie;
import Models.User;
import Services.RecommendMoviesValidator;

class FileWriterPathCoverage {

	InOut.FileWriter fileWriter = new InOut.FileWriter();
    private final String recommendFiles="RecommenedFileAmrTest.txt";

    RecommendMoviesValidator recommender = new RecommendMoviesValidator();
	   private String readAll(String filePath) throws Exception {
	        StringBuilder sb = new StringBuilder();
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                sb.append(line).append("\n");
	            }
	        }
	        return sb.toString();
	    }
    @Test
    void testWriteRecommendations_SingleUser_NoRecommendations() throws Exception {
        String outPath = recommendFiles;

        User u = new User("John", "12345678A", List.of());
        List<User> users = List.of(u);

        Movie m1 = new Movie("Matrix", "M123", List.of("Action"));
        List<Movie> movies = List.of(m1);

        fileWriter.writeRecommendations(outPath, users, movies, recommender);

        String content = readAll(outPath);

        assertEquals(
                "John,12345678A\n\n",
                content
        );
    }
    
    
    
    @Test
    void testWriteRecommendations_NoUsers() throws Exception {
        String path = recommendFiles;

        List<User> users = List.of();
        List<Movie> movies = List.of();


        fileWriter.writeRecommendations(path, users, movies, recommender);

       String lines = readAll(path);
        assertEquals("",lines);
    }
    
    @AfterEach
    public void cleanup() throws IOException {
     

        // Delete test files if they exist
        String[] filesToDelete = {recommendFiles };
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

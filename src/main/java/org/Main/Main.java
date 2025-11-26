package org.Main;



import Models.Movies;
import Models.Users;
import InOut.FileReaderService;
import InOut.FileWriterService;
import Services.OutputValidator;
import Services. recommendMovies;

import java.io.FileWriter;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // File paths (you may change them)
        String moviesFile = "movies.txt";
        String usersFile = "users.txt";
        String outputFile = "recommendations.txt";

        FileReaderService fileReader = new FileReaderService();
        FileWriterService fileWriter = new FileWriterService();
        recommendMovies recommender = new  recommendMovies();

        try {
            // ------------------------------
            // Read & validate movies file
            // ------------------------------
           // System.out.println("movies ");
            List<Movies> movies = fileReader.readMovies(moviesFile);
          //  System.out.println("movies true");
            // ------------------------------
            // Read & validate users file
            // ------------------------------
            List<Users> users = fileReader.readUsers(usersFile);
           // System.out.println("users true");
            // ------------------------------
            // If everything is valid → write recommendations
            // ------------------------------
            fileWriter.writeRecommendations(outputFile, users, movies, recommender);

            System.out.println("SUCCESS → recommendations.txt generated");

        } catch (Exception e) {

            // ------------------------------------------------------------
            // Write ONLY the first error to output file as required
            // ------------------------------------------------------------
            try {
                FileWriter writer = new FileWriter("recommendation.txt");
                writer.write(e.getMessage());
                writer.close();
                System.out.println("ERROR → written to recommendations.txt");
            } catch (Exception ex) {
                System.out.println("Failed to write error file: " + ex.getMessage());
            }

        }
    }
}

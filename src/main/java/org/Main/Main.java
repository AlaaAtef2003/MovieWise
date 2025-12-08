package org.Main;



import Models.Movie;
import Models.User;
import InOut.FileReader;
import InOut.FileWriter;
import Services.RecommendMoviesValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // File paths (you may change them)
        String moviesFile = "movies.txt";
        String usersFile = "users.txt";
        String outputFile = "recommendations.txt";

        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        try {
            // ------------------------------
            // Read & validate movies file
            // ------------------------------
            List<Movie> movies = fileReader.readMovies(moviesFile);

            // ------------------------------
            // Read & validate users file
            // ------------------------------
            List<User> users = fileReader.readUsers(usersFile);

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
                java.io.FileWriter writer = new java.io.FileWriter("recommendation.txt");
                writer.write(e.getMessage());
                writer.close();
                System.out.println("ERROR → written to recommendations.txt");
            } catch (Exception ex) {
                System.out.println("Failed to write error file: " + ex.getMessage());
            }

        }
    }
}

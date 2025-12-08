package Integration;

import InOut.FileReader;
import InOut.FileWriter;
import Models.Movie;
import Models.User;
import Services.RecommendMoviesValidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Integration {

    public void outputFile(String usersFile, String moviesFile, String outputFile) {
        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        RecommendMoviesValidator recommender = new RecommendMoviesValidator();

        try {

            if (Files.size(Path.of(moviesFile)) == 0) {
                throw new RuntimeException("ERROR: Movie file is empty");
            }

            if (Files.size(Path.of(usersFile)) == 0) {
                throw new RuntimeException("ERROR: User file is empty");
            }

            List<Movie> movies = fileReader.readMovies(moviesFile);
            List<User> users = fileReader.readUsers(usersFile);

            fileWriter.writeRecommendations(outputFile, users, movies, recommender);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

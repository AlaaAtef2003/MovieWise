package InOut;


import Models.Movie;
import Models.User;
import Services.recommendMovies;

import java.io.FileWriter;
import java.util.List;

public class FileWriterService {

    public void writeRecommendations(
            String filePath,
            List<User> users,
            List<Movie> movies,
            recommendMovies recommender
    ) throws Exception {

        FileWriter writer = new FileWriter(filePath);

        for (User user : users) {
            // Line 1: user's name, id
            writer.write(user.getName() + "," + user.getUserId() + "\n");

            // Line 2: recommended movie titles
            List<String> recommended = recommender.recommendMovies(user, movies);

            String joined = String.join(",", recommended);
            writer.write(joined + "\n");
        }

        writer.close();
    }
}

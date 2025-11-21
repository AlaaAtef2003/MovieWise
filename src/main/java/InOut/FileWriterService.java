package InOut;


import Models.Movies;
import Models.Users;
import Services.OutputValidator;
import Services.recommendMovies;

import java.io.FileWriter;
import java.util.List;

public class FileWriterService {

    public void writeRecommendations(
            String filePath,
            List<Users> users,
            List<Movies> movies,
            recommendMovies recommender
    ) throws Exception {
        OutputValidator ov = new OutputValidator();

        FileWriter writer = new FileWriter(filePath);

        for (Users user : users) {
            // Line 1: user's name, id
            String UserLine =  user.getName() + "," + user.getUserId();

            ov.ValidateUserLine(UserLine,users);

            writer.write(UserLine+ "\n");

            // Line 2: recommended movie titles
            List<String> recommended = recommender.recommendMovies(user, movies);
            ov.Validaterecomend(recommended,movies);

            String joined = String.join(",", recommended);
            writer.write(joined + "\n");
        }

        writer.close();
    }
}

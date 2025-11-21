package Services;

import Models.Movies;
import Models.Users;

import java.util.HashSet;
import java.util.List;

public class OutputValidator {
    public void ValidateUserLine(String userline, List<Users> users) {
        String[] parts = userline.split(",\\s*");

        if (parts.length != 2) {
            throw new RuntimeException("ERROR: User Name or User Id format is wrong");
        }

        String username = parts[0].trim();
        String userid = parts[1].trim();
        if (username.isEmpty() || userid.isEmpty())
            throw new RuntimeException("ERROR: Name or ID is empty");
        if (!userid.matches("\\d+"))
            throw new RuntimeException("ERROR: ID must be numeric");
        validatematch(username, userid, users);

    }

    private void validatematch(String username, String userid, List<Users> users) {

        for (Users user : users) {

            if (user.getName().equals(username)) {

                // username exists → check id
                if (!user.getUserId().equals(userid)) {
                    throw new RuntimeException("Username doesn't match with id");
                }

                // valid match
                return;
            }
        }

        // loop finished → no username found
        throw new RuntimeException("User doesn't exist");
    }
    public void Validaterecomend(List<String> recommended,List<Movies> movies){
        HashSet<String> seen = new HashSet<>();
        for(var title:recommended){
            title=title.trim();
            if(title.isEmpty())
                throw new RuntimeException("ERROR: Empty movie title found");
            if(seen.contains(title))
                throw new RuntimeException("ERROR: Duplicate movie recommendation: "+title);
            seen.add(title);
            String finalTitle = title;
            boolean exists = movies.stream().anyMatch(m -> m.getTitle().equals(finalTitle));
            if (!exists)
                throw new RuntimeException("ERROR: Movie not found in database: " + title);


        }



    }
}
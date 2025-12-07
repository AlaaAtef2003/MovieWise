package Services;

import Models.Movie;
import Models.User;

import java.util.HashSet;
import java.util.List;

public class OutputValidator {

    private final List<User> users;
    private final List<Movie> movies;

    public OutputValidator(List<User> users, List<Movie> movies) {
        this.users = users;
        this.movies = movies;
    }

    // --------------------------------------------------------------
    // VALIDATE USER LINE IN OUTPUT
    // --------------------------------------------------------------
    public void validateUserLine(String userLine) {

        String[] parts = userLine.split(",\\s*");

        if (parts.length != 2) {
            throw new RuntimeException("ERROR: User Name or User Id format is wrong");
        }

        String username = parts[0].trim();
        String userId = parts[1].trim();

        if (username.isEmpty() || userId.isEmpty()) {
            throw new RuntimeException("ERROR: User Name or User Id is wrong");
        }

        // 1) Validate username format
        if (!username.matches("^[A-Za-z]+( [A-Za-z]+)*$")) {
            throw new RuntimeException("ERROR: User Name " + username + " is wrong");
        }

        // 2) Validate user ID format: 8 digits + optional letter
        if (!userId.matches("^[0-9]{8}[A-Za-z]?$")) {
            throw new RuntimeException("ERROR: User Id " + userId + " is wrong");
        }

        // 3) Check ID belongs to exactly one user (existence & uniqueness)
        long count = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .count();

        if (count != 1) {
            throw new RuntimeException("ERROR: User Id " + userId + " is wrong");
        }

        // 4) Optional: check name matches ID (if your team requires that)
        boolean matched = users.stream()
                .anyMatch(u -> u.getUserId().equals(userId) && u.getName().equals(username));

        if (!matched) {
            throw new RuntimeException("ERROR: Username does not match with Id");
        }
    }

    // --------------------------------------------------------------
    // VALIDATE RECOMMENDED MOVIES FOR ONE USER
    // --------------------------------------------------------------
    public void validateRecommendation(List<String> recommended, List<String> likedByuser) {

        // ------------------------------------------------------------
        // STEP 1 — Collect expected genres from the movies user liked
        // ------------------------------------------------------------
        HashSet<String> expectedGenres = new HashSet<>();

        for (String likedId : likedByuser) {
            Movie likedMovie = movies.stream()
                    .filter(m -> m.getMovieId().equals(likedId))
                    .findFirst()
                    .orElse(null);

            if (likedMovie != null) {
                expectedGenres.addAll(likedMovie.getGenres());
            }
        }

        // ------------------------------------------------------------
        // STEP 2 — Validate recommended movies
        // ------------------------------------------------------------
        HashSet<String> seen = new HashSet<>();

        for (String title : recommended) {

            if (title == null || title.trim().isEmpty()) {
                throw new RuntimeException("ERROR: Movie Title is wrong");
            }

            String cleanTitle = title.trim();

            // 1) FORMAT CHECK
            if (!cleanTitle.matches("([A-Z][a-zA-Z]*)( [A-Z][a-zA-Z]*)*")) {
                throw new RuntimeException("ERROR: Movie Title " + cleanTitle + " is wrong");
            }

            // 2) DUPLICATE CHECK
            if (seen.contains(cleanTitle)) {
                throw new RuntimeException("ERROR: Duplicate movie recommendation: " + cleanTitle);
            }
            seen.add(cleanTitle);

            // 3) EXISTENCE CHECK (must exist in movies database)
            Movie movie = movies.stream()
                    .filter(m -> m.getTitle().equals(cleanTitle))
                    .findFirst()
                    .orElse(null);

            if (movie == null) {
                throw new RuntimeException("ERROR: Movie not found in database: " + cleanTitle);
            }

            // 4) GENRE CHECK — must belong to at least one expected genre
            boolean matchesGenre = movie.getGenres()
                    .stream()
                    .anyMatch(expectedGenres::contains);

            if (!matchesGenre) {
                throw new RuntimeException(
                        "ERROR: Movie " + cleanTitle + " does not match recommended genres"
                );
            }
        }
    }

}
/*
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
        for(String title:recommended){
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
*/
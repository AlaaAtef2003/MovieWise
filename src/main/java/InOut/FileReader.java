package InOut;

import Models.Movie;
import Models.User;
import Services.InputValidator;

import java.io.BufferedReader;
import java.util.*;

public class FileReader {

    private final InputValidator validator = new InputValidator();

    // --------------------------
    // Read Movies
    // --------------------------
    public List<Movie> readMovies(String filePath) throws Exception {
        List<Movie> movies = new ArrayList<>();
        Set<String> usedMovieIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String titleIdLine;
            while ((titleIdLine = reader.readLine()) != null   ) {
                // Skip empty or spaces-only lines
                if (titleIdLine.trim().isEmpty()) {
                    continue;
                }
                String genresLine = reader.readLine();  // next line must exist
                if (genresLine == null) break;

                Movie movie = parseMovie(titleIdLine, genresLine);
//                String string = movie.toString();
//                System.out.println(string);
                validator.validateMovie(movie, usedMovieIds);
                movies.add(movie);

            }
        } catch (Exception e) {
            //System.out.println( e);
            throw new RuntimeException( e.getMessage(), e);
        }

        return movies;
    }

    private Movie parseMovie(String titleIdLine, String genresLine) {

        String[] parts = titleIdLine.split("[،，,]\\s*");
//        System.out.println(x);
      //  System.out.println(titleIdLine + parts.length);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid movie line format" );
        }

        String title = parts[0].trim();
        String id = parts[1].trim();

        List<String> genres = Arrays.asList(genresLine.split(",\\s*"));
        Movie m = new Movie(title, id, genres);
//        String string = m.toString();
//        System.out.println(string);
        return new Movie(title, id, genres);
    }

    // --------------------------
    // Read Users
    // --------------------------
    public List<User> readUsers(String filePath) throws Exception {
        List<User> users = new ArrayList<>();
        Set<String> usedUserIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String nameIdLine;


            while ((nameIdLine = reader.readLine()) != null) {


                if (nameIdLine.trim().isEmpty()) {
                    continue;
                }
                String likedMoviesLine = reader.readLine();
                if (likedMoviesLine == null) break;

                User user = parseUser(nameIdLine, likedMoviesLine);
                validator.validateUser(user, usedUserIds);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading users: " + e.getMessage(), e);
        }

        return users;
    }

    private User parseUser(String nameIdLine, String likedMoviesLine) {
        String[] parts = nameIdLine.split("[،，,]\\s*");
       // System.out.println(parts[0]+ "       "+ parts[1]);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid user line format" );
        }
        if (parts[0].startsWith(" "))throw new RuntimeException("ERROR: User Name " +  parts[0] + " is wrong");

        String name = parts[0].trim();
        String id = parts[1].trim();
        List<String> likedIds = Arrays.asList(likedMoviesLine.split(",\\s*"));

        return new User(name, id, likedIds);
    }

}
package InOut;

import Models.Movies;
import Models.Users;
import Services.InputValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FileReaderService {

    private final InputValidator validator = new InputValidator();

    // --------------------------
    // Read Movies
    // --------------------------
    public List<Movies> readMovies(String filePath) throws Exception {
        List<Movies> movies = new ArrayList<>();
        Set<String> usedMovieIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String titleIdLine;
            while ((titleIdLine = reader.readLine()) != null   ) {
                // Skip empty or spaces-only lines
                if (titleIdLine.trim().isEmpty()) {
                    continue;
                }
                String genresLine = reader.readLine();  // next line must exist
                if (genresLine == null) break;

                Movies movie = parseMovie(titleIdLine, genresLine);
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

    private Movies parseMovie(String titleIdLine, String genresLine) {

        String[] parts = titleIdLine.split("[،，,]\\s*");
//        System.out.println(x);
      //  System.out.println(titleIdLine + parts.length);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid movie line format" );
        }

        String title = parts[0].trim();
        String id = parts[1].trim();

        List<String> genres = Arrays.asList(genresLine.split(",\\s*"));
        Movies m = new Movies(title, id, genres);
//        String string = m.toString();
//        System.out.println(string);
        return new Movies(title, id, genres);
    }

    // --------------------------
    // Read Users
    // --------------------------
    public List<Users> readUsers(String filePath) throws Exception {
        List<Users> users = new ArrayList<>();
        Set<String> usedUserIds = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String nameIdLine;


            while ((nameIdLine = reader.readLine()) != null) {


                if (nameIdLine.trim().isEmpty()) {
                    continue;
                }
                String likedMoviesLine = reader.readLine();
                if (likedMoviesLine == null) break;

                Users user = parseUser(nameIdLine, likedMoviesLine);
                validator.validateUser(user, usedUserIds);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading users: " + e.getMessage(), e);
        }

        return users;
    }

    private Users parseUser(String nameIdLine, String likedMoviesLine) {
        String[] parts = nameIdLine.split("[،，,]\\s*");
       // System.out.println(parts[0]+ "       "+ parts[1]);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid user line format" );
        }
        if (parts[0].startsWith(" "))throw new RuntimeException("ERROR: User Name " +  parts[0] + " is wrong");

        String name = parts[0].trim();
        String id = parts[1].trim();
        List<String> likedIds = Arrays.asList(likedMoviesLine.split(",\\s*"));

        return new Users(name, id, likedIds);
    }

}
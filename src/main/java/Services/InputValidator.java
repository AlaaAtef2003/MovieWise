package Models.src.main.java.Services;

import Models.Movies;
import Models.Users;

import java.util.Set;

public class InputValidator {


    // -------------------------------------------------
    // INTEGRATION FUNCTIONS (MAIN ENTRY POINTS)
    // -------------------------------------------------

    public void validateMovie(Movies movie, Set<String> usedMovieIds) {

      //  System.out.println("title true");
        validateMovieTitle(movie.getTitle());                          // 1
       // System.out.println("title 1");
        validateMovieIdLetters(movie.getTitle(), movie.getMovieId());  // 2
      //  System.out.println("title 2");

       // System.out.println("title 3");
        ensureMovieIdIsUnique(movie.getMovieId(), movie.getTitle(),  usedMovieIds);



    }

    public void validateUser(Users user, Set<String> usedUserIds) {
        System.out.println("user 1");
        validateUserName(user.getName());              // 1
        System.out.println("user 2");
        validateUserIdFormat(user.getUserId());        // 2
        System.out.println("user 3");
        checkUserIdUniqueness(user.getUserId(), usedUserIds); // 3
    }


    // -------------------------------------------------
    // MOVIE VALIDATION FUNCTIONS (SMALL / DIVIDED)
    // -------------------------------------------------

    private void validateMovieTitle(String title) {
        if (!title.matches("([A-Z][a-zA-Z]*)( [A-Z][a-zA-Z]*)*")) {
            throw new RuntimeException("ERROR: Movie Title " + title + " is wrong");
        }
    }

    private void validateMovieIdLetters(String title, String id) {
        String letters = extractCapitalLetters(title);
       // System.out.println( letters);

        if (!id.startsWith(letters) || (3+ letters.length()) != id.length() )   {
            throw new RuntimeException("ERROR: Movie Id letters " + id + " are wrong");
        }
    }



    public void ensureMovieIdIsUnique(String movieId, String movieTitle, Set<String> usedMovieIds) {

        String numbers = GetNumberofId( movieTitle,movieId);
       // System.out.println(numbers);
        // Must be exactly 3 digits
        if (!numbers.matches("\\d{3}")) {
            throw new RuntimeException("ERROR: Movie Id numbers " + movieId + " aren’t unique");
        }

//        // Must be UNIQUE digits
//        if (numbers.chars().distinct().count() != 3) {
//            throw new RuntimeException("ERROR: Movie Id numbers " + movieId + " aren’t unique");
//        }

        if (usedMovieIds.contains(GetNumberofId(movieTitle,movieId))) {
            throw new RuntimeException(
                    "ERROR: Movie Id numbers " + movieId + " aren’t unique"
            );
        }

        usedMovieIds.add(GetNumberofId(movieTitle,movieId));
    }

    private String GetNumberofId(String movieTitle,String movieId){
        String letters = extractCapitalLetters(movieTitle);
        String numbers = movieId.substring(letters.length());
        return numbers;
    }


    private String extractCapitalLetters(String title) {
        return title.replaceAll("[^A-Z]", "");
    }





    // -------------------------------------------------
    // USER VALIDATION FUNCTIONS (SMALL / DIVIDED)
    // -------------------------------------------------

    private void validateUserName(String name) {
        if (!name.matches("[A-Za-z]+( [A-Za-z]+)*")) {
            throw new RuntimeException("ERROR: User Name " + name + " is wrong");
        }
    }

    private void validateUserIdFormat(String uid) {
        if (!uid.matches("\\d{8}[A-Za-z]?")) {
            throw new RuntimeException("ERROR: User Id " + uid + " is wrong");
        }
    }

    private void checkUserIdUniqueness(String uid, Set<String> usedIds) {
        if (usedIds.contains(uid)) {
            throw new RuntimeException("ERROR: User Id " + uid + " is wrong");
        }
        usedIds.add(uid);
    }
}

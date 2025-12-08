package Services;

import Models.Movie;
import Models.User;

import java.util.*;

public class RecommendMoviesValidator {

        public List<String> recommendMovies(User user, List<Movie> movies) {
            Set<String> likedGenres = new HashSet<>();

            // collect liked genres
            for (String likedId : user.getLikedMovieIds()) {
                for (Movie m : movies) {
                    if (m.getMovieId().equals(likedId)) {
                        likedGenres.addAll(m.getGenres());
                    }
                }
            }

            Set<String> recommendedTitles = new LinkedHashSet<>();

            // recommend movies with same genre
            for (Movie m : movies) {
                for (String g : m.getGenres()) {
                    if (likedGenres.contains(g)) {
                        recommendedTitles.add(m.getTitle());
                    }
                }
            }

            return new ArrayList<>(recommendedTitles);
        }
    }


package Models.src.main.java.Services;

import Models.Movies;
import Models.Users;

import java.util.*;

public class recommendMovies {

        public List<String> recommendMovies(Users user, List<Movies> movies) {
            Set<String> likedGenres = new HashSet<>();

            // collect liked genres
            for (String likedId : user.getLikedMovieIds()) {
                for (Movies m : movies) {
                    if (m.getMovieId().equals(likedId)) {
                        likedGenres.addAll(m.getGenres());
                    }
                }
            }

            Set<String> recommendedTitles = new LinkedHashSet<>();

            // recommend movies with same genre
            for (Movies m : movies) {
                for (String g : m.getGenres()) {
                    if (likedGenres.contains(g)) {
                        recommendedTitles.add(m.getTitle());
                    }
                }
            }

            return new ArrayList<>(recommendedTitles);
        }
    }


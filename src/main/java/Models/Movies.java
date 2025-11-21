package Models;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Movies {
    private String title;
    private String movieId;
    private List<String> genres;

    public Movies(String title, String movieId, List<String> genres) {
        this.title = title;
        this.movieId = movieId;
        this.genres = genres;
    }

    public String getTitle() { return title; }
    public String getMovieId() { return movieId; }
    public List<String> getGenres() { return genres; }

    public void setTitle(String title) { this.title = title; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public void setGenres(List<String> genres) { this.genres = genres; }



    @Override
    public String toString() {
        return "Movie{title='" + title + "', movieId='" + movieId + "', genres=" + genres + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movies movie = (Movies) obj;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return movieId.hashCode();
    }
}
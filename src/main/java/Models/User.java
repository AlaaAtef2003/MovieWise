package Models;


import java.util.List;

public class User {
    private String name;
    private String userId;
    private List<String> likedMovieIds;

    public User(String name, String userId, List<String> likedMovieIds) {
        this.name = name;
        this.userId = userId;
        this.likedMovieIds = likedMovieIds;
    }

    public String getName() { return name; }
    public String getUserId() { return userId; }
    public List<String> getLikedMovieIds() { return likedMovieIds; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setLikedMovieIds(List<String> likedMovieIds) {
        this.likedMovieIds = likedMovieIds;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', userId='" + userId +
                "', likedMovieIds=" + likedMovieIds + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
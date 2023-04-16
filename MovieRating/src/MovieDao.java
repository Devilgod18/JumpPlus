
import java.util.List;

public interface MovieDao {
  void addMovie(Movie movie);
  void updateMovie(Movie movie,User loggedInUser);
  void deleteMovie(String name, User loggedInUser);
  Movie getMovieByName(String name);
  List<Movie> getAllMovies();
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {
  
  private static final String INSERT_MOVIE_SQL = "INSERT INTO movies (name, rating, num_ratings) VALUES (?, ?, ?)";
  private static final String UPDATE_MOVIE_SQL = "UPDATE movies SET rating = ?, num_ratings = ? WHERE name = ?";
  private static final String DELETE_MOVIE_SQL = "DELETE FROM movies WHERE name = ?";
  private static final String SELECT_MOVIE_BY_NAME_SQL = "SELECT * FROM movies WHERE name = ?";
  private static final String SELECT_ALL_MOVIES_SQL = "SELECT * FROM movies";
  
  @Override
  public void addMovie(Movie movie) {
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_MOVIE_SQL)) {
      statement.setString(1, movie.getName());
      statement.setDouble(2, movie.getRating());
      statement.setInt(3, movie.getNumRatings());
      statement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateMovie(Movie movie, User loggedInUser) {
    if (loggedInUser != null && loggedInUser.isAdmin()) {
      try {
		try (Connection connection = Database.getConnection();
		       PreparedStatement statement = connection.prepareStatement(UPDATE_MOVIE_SQL)) {
		    statement.setDouble(1, movie.getRating());
		    statement.setInt(2, movie.getNumRatings());
		    statement.setString(3, movie.getName());
		    statement.executeUpdate();
		  } catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    } else {
      System.out.println("\nYou must be an admin to update movies.");
    }
  }

  @Override
  public void deleteMovie(String name, User loggedInUser) {
    if (loggedInUser != null && loggedInUser.isAdmin()) {
      try (Connection connection = Database.getConnection();
           PreparedStatement statement = connection.prepareStatement(DELETE_MOVIE_SQL)) {
        statement.setString(1, name);
        statement.executeUpdate();
      } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("\nYou must be an admin to delete movies.");
    }
  }

  @Override
  public Movie getMovieByName(String name) {
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_MOVIE_BY_NAME_SQL)) {
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        double rating = resultSet.getDouble("rating");
        int numRatings = resultSet.getInt("num_ratings");
        return new Movie(name, rating, numRatings);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Movie> getAllMovies() {
    List<Movie> movies = new ArrayList<>();
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MOVIES_SQL)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        double rating = resultSet.getDouble("rating");
        int numRatings = resultSet.getInt("num_ratings");
        Movie movie = new Movie(name, rating, numRatings);
        movies.add(movie);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return movies;
  }
}

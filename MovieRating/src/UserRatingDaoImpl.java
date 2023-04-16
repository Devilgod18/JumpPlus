import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRatingDaoImpl implements UserRatingDao {

  private static final String INSERT_USER_RATING_SQL = "INSERT INTO user_ratings (user_email, movie_name, rating) VALUES (?, ?, ?)";
  private static final String UPDATE_USER_RATING_SQL = "UPDATE user_ratings SET rating = ? WHERE user_email = ? AND movie_name = ?";
  private static final String DELETE_USER_RATING_SQL = "DELETE FROM user_ratings WHERE user_email = ? AND movie_name = ?";
  private static final String SELECT_USER_RATINGS_BY_EMAIL_SQL = "SELECT * FROM user_ratings WHERE user_email = ?";
  private static final String SELECT_USER_RATING_BY_EMAIL_AND_MOVIE_NAME_SQL = "SELECT * FROM user_ratings WHERE user_email = ? AND movie_name = ?";

  @Override
  public void addUserRating(UserRating userRating) {
    try (Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_USER_RATING_SQL)) {
      statement.setString(1, userRating.getEmail());
      statement.setString(2, userRating.getMovieName());
      statement.setInt(3, userRating.getRating());
      statement.executeUpdate();
      MovieDao movieDao = new MovieDaoImpl();
      Movie movie = movieDao.getMovieByName(userRating.getMovieName());
      int numRatings = movie.getNumRatings() + 1;
      movie.setNumRatings(numRatings);
      movieDao.updateMovie(movie, null);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateUserRating(UserRating userRating) {
    try (Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER_RATING_SQL)) {
      statement.setInt(1, userRating.getRating());
      statement.setString(2, userRating.getEmail());
      statement.setString(3, userRating.getMovieName());
      statement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteUserRating(UserRating userRating) {
    try (Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER_RATING_SQL)) {
      statement.setString(1, userRating.getEmail());
      statement.setString(2, userRating.getMovieName());
      statement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<UserRating> getUserRatingsByEmail(String email) {
    List<UserRating> userRatings = new ArrayList<>();
    try (Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_USER_RATINGS_BY_EMAIL_SQL)) {
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String movieName = resultSet.getString("movie_name");
        int rating = resultSet.getInt("rating");
        UserRating userRating = new UserRating(email, movieName, rating);
        userRatings.add(userRating);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return userRatings;
  }

  @Override
  public UserRating getUserRatingByEmailAndMovieName(String email, String movieName) {
    try (Connection connection = Database.getConnection();
        PreparedStatement statement = connection
            .prepareStatement(SELECT_USER_RATING_BY_EMAIL_AND_MOVIE_NAME_SQL)) {
      statement.setString(1, email);
      statement.setString(2, movieName);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        int rating = resultSet.getInt("rating");
        UserRating userRating = new UserRating(email, movieName, rating);
        return userRating;
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}


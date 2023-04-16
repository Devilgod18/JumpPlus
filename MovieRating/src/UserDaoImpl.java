import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
  
  private static final String INSERT_USER_SQL = "INSERT INTO users (email, password, is_admin) VALUES (?, ?, ?)";
  private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD_SQL = "SELECT * FROM users WHERE email = ? AND password = ?";
  private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users WHERE is_admin = ?";
  private static final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";

  @Override
  public void addUser(User user) {
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {
      statement.setString(1, user.getEmail());
      statement.setString(2, user.getPassword());
      statement.setBoolean(3, user.isAdmin());
      statement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User getUserByEmailAndPassword(String email, String password) {
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD_SQL)) {
      statement.setString(1, email);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        String userEmail = resultSet.getString("email");
        String userPassword = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("is_admin");
        User user = new User(userEmail, userPassword);
        user.setAdmin(isAdmin);
        return user;
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (Connection connection = Database.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_SQL)) {
      statement.setBoolean(1, false);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("is_admin");
        User user = new User(email, password);
        user.setAdmin(isAdmin);
        users.add(user);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return users;
  }
  @Override
  public User getUserByEmail(String email) {
      try (Connection connection = Database.getConnection();
           PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL)) {
          statement.setString(1, email);
          ResultSet resultSet = statement.executeQuery();
          if (resultSet.next()) {
              String userEmail = resultSet.getString("email");
              String userPassword = resultSet.getString("password");
              boolean isAdmin = resultSet.getBoolean("is_admin");
              User user = new User(userEmail, userPassword);
              user.setAdmin(isAdmin);
              return user;
          }
      } catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
      }
      return null;
  }

}

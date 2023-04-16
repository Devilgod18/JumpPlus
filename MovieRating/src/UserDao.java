import java.util.List;

public interface UserDao {
  void addUser(User user);
  User getUserByEmailAndPassword(String email, String password);
  List<User> getAllUsers();
  User getUserByEmail(String email);
}

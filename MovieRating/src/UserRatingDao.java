import java.util.List;

public interface UserRatingDao {
  
  void addUserRating(UserRating userRating);
  
  void updateUserRating(UserRating userRating);
  
  void deleteUserRating(UserRating userRating);
  
  List<UserRating> getUserRatingsByEmail(String email);
  
  UserRating getUserRatingByEmailAndMovieName(String email, String movieName);
}

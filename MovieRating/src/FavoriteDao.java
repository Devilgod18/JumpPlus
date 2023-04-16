import java.util.List;

public interface FavoriteDao {
  void addFavorite(String email, String movieName);
  void deleteFavorite(String email, String movieName);
  List<String> getFavoritesByEmail(String email);
}

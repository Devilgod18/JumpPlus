public class UserRating {
  private String email;
  private String movieName;
  private int rating;
  
  public UserRating(String email, String movieName, int rating) {
    this.email = email;
    this.movieName = movieName;
    this.rating = rating;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getMovieName() {
    return movieName;
  }
  
  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }
  
  public int getRating() {
    return rating;
  }
  
  public void setRating(int rating) {
    this.rating = rating;
  }
}

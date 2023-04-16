public class Movie {
  
  private String name;
  private double rating;
  private int numRatings;
  
  public Movie(String name, double rating, int numRatings) {
    this.name = name;
    this.rating = rating;
    this.numRatings = numRatings;
  }
  
  public String getName() {
    return name;
  }
  
  public double getRating() {
    return rating;
  }
  
  public int getNumRatings() {
    return numRatings;
  }
  
  public void setRating(double rating) {
    this.rating = rating;
  }
  
  public void setNumRatings(int numRatings) {
    this.numRatings = numRatings;
  }
  
  public double getAverageRating() {
    return numRatings > 0 ? rating / numRatings : 0;
  }
  
}

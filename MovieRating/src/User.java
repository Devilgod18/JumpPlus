public class User {
  private String email;
  private String password;
  private boolean isAdmin;
  
  public User(String email, String password) {
    this.email = email;
    this.password = password;
    this.isAdmin = false;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public boolean isAdmin() {
    return isAdmin;
  }
  
  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}


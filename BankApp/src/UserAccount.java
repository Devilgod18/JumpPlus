public class UserAccount {
    private String username;
    private String password;
    private String accountName;
    private double balance;

    public UserAccount(String username, String password, String accountName, double balance) {
        this.username = username;
        this.password = password;
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

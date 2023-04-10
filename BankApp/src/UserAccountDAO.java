import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserAccountDAO {
    private final Connection connection;

    public UserAccountDAO() throws ClassNotFoundException, SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public UserAccount findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM user_accounts WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            String accountName = resultSet.getString("account_name");
            double balance = resultSet.getDouble("balance");

            return new UserAccount(username, password, accountName, balance);
        }

        return null;
    }

    public boolean createUserAccount(String username, String password, String accountName) throws SQLException {
        String query = "INSERT INTO user_accounts (username, password, account_name, balance) VALUES (?, ?, ?, 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, accountName);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }

    public boolean deposit(String username, double amount) throws SQLException {
        String query = "UPDATE user_accounts SET balance = balance + ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setString(2, username);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }

    public boolean withdraw(String username, double amount) throws SQLException {
        String query = "UPDATE user_accounts SET balance = balance - ? WHERE username = ? AND balance >= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setString(2, username);
        preparedStatement.setDouble(3, amount);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }

    public boolean transfer(String sourceUsername, String destinationUsername, double amount) throws SQLException {
        connection.setAutoCommit(false);

        try {
            if (!withdraw(sourceUsername, amount)) {
                connection.rollback();
                return false;
            }

            if (!deposit(destinationUsername, amount)) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    public double getBalance(String username) throws SQLException {
        String query = "SELECT balance FROM user_accounts WHERE username = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            } else {
                throw new SQLException("No user account found with the specified username.");
            }
        }
    }
    public void createTransaction(String username, String type, double amount, String description) throws SQLException {
        String query = "INSERT INTO transactions (username, type, amount, description) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate();
        }
    }

   
    public List<Transaction> getRecentTransactions(String username) throws SQLException {
        String query = "SELECT * FROM transactions WHERE username = ? ORDER BY created_at DESC LIMIT 5";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("type"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("created_at")
                );
                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
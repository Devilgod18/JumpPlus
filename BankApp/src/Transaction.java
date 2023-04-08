import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String username;
    private String type;
    private double amount;
    private String description;
    private Timestamp createdAt;

    public Transaction(int id, String username, String type, double amount, String description, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}

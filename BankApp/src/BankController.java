import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class BankController {
	private static final Scanner scanner = new Scanner(System.in);
	
	 private static void showBankingMenu(UserAccount userAccount, UserAccountDAO userAccountDAO) {
	        while (true) {
	            System.out.println("\n1. Deposit");
	            System.out.println("2. Withdraw");
	            System.out.println("3. Check balance");
	            System.out.println("4. Transfer");
	            System.out.println("5. View 5 most recent transactions");
	            System.out.println("6. Logout");

	            System.out.print("Please enter your choice: ");
	            int choice = scanner.nextInt();

	            try {
	                switch (choice) {
	                    case 1:
	                        deposit(userAccount, userAccountDAO);
	                        break;
	                    case 2:
	                        withdraw(userAccount, userAccountDAO);
	                        break;
	                    case 3:
	                        checkBalance(userAccount, userAccountDAO);
	                        break;
	                    case 4:
	                        transfer(userAccount, userAccountDAO);
	                        break;
	                    case 5:
	                    	viewRecentTransactions(userAccount, userAccountDAO);
	                        break;
	                    case 6:
	                        System.out.println("Logging out...");
	                        return;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            } catch (SQLException e) {
	                System.out.println("An error occurred while processing your request. Please try again.");
	                e.printStackTrace();
	            }
	        }
	    }
	private static void viewRecentTransactions(UserAccount userAccount, UserAccountDAO userAccountDAO) throws SQLException {
        List<Transaction> transactions = userAccountDAO.getRecentTransactions(userAccount.getUsername());

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n5 Most Recent Transactions:");
            for (Transaction transaction : transactions) {
                System.out.printf(Color.CYAN+"%s: %s %s (ID: %d, Amount: %.2f, Date: %s)\n"+Color.RESET,
                    transaction.getType(),
                    transaction.getDescription(),
                    userAccount.getAccountName(),
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getCreatedAt()
                    
                );
            }
        }
    }
	public static void register(UserAccountDAO userAccountDAO) {
        System.out.print("Enter your desired username: ");
        String username = scanner.next();

        System.out.print("Enter your desired password: ");
        String password = scanner.next();

        System.out.print("Enter your account name: ");
        String accountName = scanner.next();

        try {
            if (userAccountDAO.createUserAccount(username, password, accountName)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while registering your account. Please try again.");
            e.printStackTrace();
        }
    }

    public static void login(UserAccountDAO userAccountDAO) {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        System.out.print("Enter your password: ");
        String password = scanner.next();

        try {
            UserAccount userAccount = userAccountDAO.findByUsername(username);

            if (userAccount != null && userAccount.getPassword().equals(password)) {
                System.out.println("Login successful!");
                showBankingMenu(userAccount, userAccountDAO);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while logging in. Please try again.");
            e.printStackTrace();
        }
    }

    
    private static void deposit(UserAccount userAccount, UserAccountDAO userAccountDAO) throws SQLException {
        System.out.print("Enter the amount you want to deposit: ");
        double amount = scanner.nextDouble();

        if (userAccountDAO.deposit(userAccount.getUsername(), amount)) {
        	userAccountDAO.createTransaction(userAccount.getUsername(), "DEPOSIT", amount, "Deposit");
            System.out.println(Color.CYAN+"Deposit successful! Your new balance is: " + userAccountDAO.getBalance(userAccount.getUsername())+Color.RESET);
        } else {
            System.out.println("Deposit failed. Please try again.");
        }
    }

    private static void withdraw(UserAccount userAccount, UserAccountDAO userAccountDAO) throws SQLException {
        System.out.print("Enter the amount you want to withdraw: ");
        double amount = scanner.nextDouble();

        if (userAccountDAO.withdraw(userAccount.getUsername(), amount)) {
        	 userAccountDAO.createTransaction(userAccount.getUsername(), "WITHDRAW", amount, "Withdraw");
            System.out.println(Color.CYAN+ "Withdrawal successful! Your new balance is: " + userAccountDAO.getBalance(userAccount.getUsername())+Color.RESET);
        } else {
            System.out.println("Withdrawal failed. Please check your balance and try again.");
        }
    }

    private static void checkBalance(UserAccount userAccount, UserAccountDAO userAccountDAO) throws SQLException {
        double balance = userAccountDAO.getBalance(userAccount.getUsername());
        System.out.println(Color.CYAN+"Your account balance is: " + balance+Color.RESET);
    }

    private static void transfer(UserAccount userAccount, UserAccountDAO userAccountDAO) throws SQLException {
        System.out.print("Enter the recipient's username: ");
        String recipientUsername = scanner.next();

        System.out.print("Enter the amount you want to transfer: ");
        double amount = scanner.nextDouble();
        UserAccount recipient = userAccountDAO.findByUsername(recipientUsername);

        if (userAccountDAO.transfer(userAccount.getUsername(), recipientUsername, amount)) {
        	userAccountDAO.createTransaction(userAccount.getUsername(), "TRANSFER_SENT", amount, "Transfer to " + recipient.getUsername());
            userAccountDAO.createTransaction(recipient.getUsername(), "TRANSFER_RECEIVED", amount, "Transfer from " + userAccount.getUsername());
            System.out.println(Color.CYAN+"Transfer successful! Your new balance is: " + userAccountDAO.getBalance(userAccount.getUsername())+Color.RESET);
        } else {
            System.out.println("Transfer failed. Please check the recipient's username and your balance, then try again.");
        }
    }
    

}

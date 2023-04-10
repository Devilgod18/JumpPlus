import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println(Color.GREEN+"Welcome to the DollarBank!"+Color.RESET);
        UserAccountDAO userAccountDAO;
        try {
            userAccountDAO = new UserAccountDAO();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(Color.BLUE+"Error initializing UserAccountDAO:"+Color.RESET);
            e.printStackTrace();
            return;
        }

        while (true) {
        	
            System.out.println("\n1. Create new account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print(Color.GREEN+"Please enter your choice: "+Color.RESET);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    BankController.register(userAccountDAO);
                    break;
                case 2:
                	BankController.login(userAccountDAO);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    
   
    
 }
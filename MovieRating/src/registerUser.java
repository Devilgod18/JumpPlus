import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerUser {

	public static void register() {
	    Scanner scanner = new Scanner(System.in);

	    // Get user input for email, password, and confirm password
	    System.out.print("Enter your email: ");
	    String email = scanner.nextLine();

	    System.out.print("Enter your password: ");
	    String password = scanner.nextLine();

	    System.out.print("Confirm your password: ");
	    String confirmPassword = scanner.nextLine();

	    // Validate email using regex pattern
	    String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
	    Pattern pattern = Pattern.compile(emailPattern);
	    Matcher matcher = pattern.matcher(email);

	    // Check if email already exists in the database
	    UserDao userDao = new UserDaoImpl();
	    if (userDao.getUserByEmail(email) != null) {
	        System.out.println("\nThis email is already in use. Please try again with a different email.");
	        return;
	    }

	    // Insert new user into the database if email is valid and not already in use
	    if (matcher.matches()) {
	        User user = new User(email, password);
	        userDao.addUser(user);
	        System.out.println("\nRegistration successful!");
	    } else {
	        System.out.println("\nInvalid email format. Please try again.");
	    }
	}

        }
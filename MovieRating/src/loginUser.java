import java.util.Scanner;

public class loginUser {

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByEmailAndPassword(email, password);

        if (user != null) {
            UserSession.getInstance().setLoggedInUser(user);
            System.out.println("\nWelcome " + email + "!\n");

            if (user.isAdmin()) {
                System.out.println("Select an option:");
                System.out.println("1. Rate a movie");
                System.out.println("2. View your ratings");
                System.out.println("3. Edit or delete a rating");
                System.out.println("4. Add or edit a movie");
                System.out.println("5. View movie ratings");
                System.out.println("6. Exit");
            } else {
                System.out.println("Select an option:");
                System.out.println("1. Rate a movie");
                System.out.println("2. View your ratings");
                System.out.println("3. Edit or delete a rating");
                System.out.println("4. Exit");
            }
        } else {
            System.out.println("\nInvalid email or password. Please try again.\n");
            login();
        }
    }
}

import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.println("\n== Main Menu ==");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("Enter choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline
			switch (choice) {
			case 1:
				loginUser.login();
				User loggedInUser = UserSession.getInstance().getLoggedInUser();
				if (loggedInUser != null) {
					showRatingMenu.show();
				}
				break;
			case 2:
				registerUser.register();
				break;
			case 3:
				running = false;
				break;
			default:
				System.out.println("\nInvalid choice, try again.");
				break;
			}
		}
		scanner.close();
	}
}

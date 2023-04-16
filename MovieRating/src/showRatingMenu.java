import java.util.List;
import java.util.Scanner;

public class showRatingMenu {

    public static void show() {
        User loggedInUser = UserSession.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("\nYou need to log in before rating movies.\n");
            loginUser.login();
            loggedInUser = UserSession.getInstance().getLoggedInUser();
        }

        UserRatingDao userRatingDao = new UserRatingDaoImpl();
        List<UserRating> userRatings = userRatingDao.getUserRatingsByEmail(loggedInUser.getEmail());

        if (userRatings.isEmpty()) {
            System.out.println("\nYou have not rated any movies yet.\n");
        } else {
            System.out.println("\nYour ratings:");
            for (UserRating userRating : userRatings) {
                System.out.println(userRating.getMovieName() + ": " + userRating.getRating());
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect an option:");
        System.out.println("1. Rate a movie");
        System.out.println("2. View your ratings");
        System.out.println("3. Edit or delete a rating");
        System.out.println("4. " + (loggedInUser.isAdmin() ? "Add or edit a movie" : "Exit"));
        if (loggedInUser.isAdmin()) {
            System.out.println("5. View movie ratings");
        }
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
            	rateMovieMenu.rateMovie();
                break;
            case 2:
                showRatingMenu.show();
                break;
            case 3:
                editRatingMenu.editOrDeleteRating();
                break;
            case 4:
                if (loggedInUser.isAdmin()) {
                    adminMenu.adminMenu();
                }
                break;
            case 5:
                if (loggedInUser.isAdmin()) {
                    viewRatingMenu.show();
                }
                break;
            default:
                break;
        }
    }
}


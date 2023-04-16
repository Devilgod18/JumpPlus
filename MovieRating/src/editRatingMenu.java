import java.util.Scanner;

public class editRatingMenu {

    public static void editOrDeleteRating() {
        User loggedInUser = UserSession.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("\nYou need to log in before rating movies.\n");
            loginUser.login();
            loggedInUser = UserSession.getInstance().getLoggedInUser();
        }

        UserRatingDao userRatingDao = new UserRatingDaoImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the name of the movie you want to edit or delete:");
        String movieName = scanner.nextLine();

        UserRating userRating = userRatingDao.getUserRatingByEmailAndMovieName(loggedInUser.getEmail(), movieName);
        if (userRating == null) {
            System.out.println("\nYou have not rated " + movieName + ".");
            return;
        }

        System.out.println("\nWhat do you want to do with your rating for " + movieName + "?");
        System.out.println("1. Edit rating");
        System.out.println("2. Delete rating");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.println("\nEnter your new rating for " + movieName + ":");
                int rating = scanner.nextInt();
                scanner.nextLine();
                userRating.setRating(rating);
                userRatingDao.updateUserRating(userRating);
                System.out.println("\nYour rating for " + movieName + " has been updated.\n");
                break;
            case 2:
                userRatingDao.deleteUserRating(userRating);
                System.out.println("\nYour rating for " + movieName + " has been deleted.\n");
                break;
            default:
                break;
        }
    }
}
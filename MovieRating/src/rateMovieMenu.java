import java.util.Scanner;

public class rateMovieMenu {

    public static void rateMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the name of the movie you want to rate:");
        String movieName = scanner.nextLine();

        MovieDao movieDao = new MovieDaoImpl();
        Movie movie = movieDao.getMovieByName(movieName);

        if (movie == null) {
            System.out.println("\nThat movie is not in the database.\n");
            return;
        }

        System.out.println("\nEnter your rating (0-10):");
        int rating = scanner.nextInt();
        scanner.nextLine();

        User loggedInUser = UserSession.getInstance().getLoggedInUser();
        UserRatingDao userRatingDao = new UserRatingDaoImpl();
        UserRating userRating = userRatingDao.getUserRatingByEmailAndMovieName(loggedInUser.getEmail(), movieName);

        if (userRating == null) {
            userRating = new UserRating(loggedInUser.getEmail(), movieName, rating);
            userRatingDao.addUserRating(userRating);
        } else {
            userRating.setRating(rating);
            userRatingDao.updateUserRating(userRating);
        }

        int numRatings = movie.getNumRatings();
        double averageRating = movie.getAverageRating();
        movie.setRating((averageRating * numRatings + rating) / (numRatings + 1));
        movie.setNumRatings(numRatings + 1);
        movieDao.updateMovie(movie, loggedInUser);

        System.out.println("\nThank you for rating the movie!\n");
    }
}

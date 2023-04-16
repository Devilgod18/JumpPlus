import java.util.List;

public class viewRatingMenu {

    public static void show() {
        User loggedInUser = UserSession.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("\nYou need to log in before rating movies.\n");
            loginUser.login();
            loggedInUser = UserSession.getInstance().getLoggedInUser();
        }

        UserRatingDao userRatingDao = new UserRatingDaoImpl();
        MovieDao movieDao = new MovieDaoImpl();
        List<Movie> movies = movieDao.getAllMovies();

        System.out.println("\nMovie ratings:");
        for (Movie movie : movies) {
            System.out.println(movie.getName() + ": " + movie.getAverageRating());
        }
    }
}
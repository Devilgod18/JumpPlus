import java.util.List;
import java.util.Scanner;

public class adminMenu {

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSelect an option:");
        System.out.println("1. Add a movie");
        System.out.println("2. Edit a movie");
        System.out.println("3. View all movies");
        System.out.println("4. Exit");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.print("\nEnter the name of the movie: ");
                String movieName = scanner.nextLine();

                MovieDao movieDao = new MovieDaoImpl();
                Movie movie = movieDao.getMovieByName(movieName);

                if (movie != null) {
                    System.out.println("\nA movie with that name already exists.");
                } else {
                    movie = new Movie(movieName, 0, 0);
                    movieDao.addMovie(movie);
                    System.out.println("\n" + movieName + " added successfully!");
                }
                adminMenu();
                break;
            case 2:
                System.out.print("\nEnter the name of the movie to edit: ");
                String movieNameToEdit = scanner.nextLine();

                movieDao = new MovieDaoImpl();
                movie = movieDao.getMovieByName(movieNameToEdit);

                if (movie == null) {
                    System.out.println("\nNo movie found with that name.");
                } else {
                    System.out.println("\nEnter the new rating for " + movieNameToEdit + ": ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("\nEnter the new number of ratings for " + movieNameToEdit + ": ");
                    int numRatings = scanner.nextInt();
                    scanner.nextLine();

                    movie.setRating(rating);
                    movie.setNumRatings(numRatings);
                    movieDao.updateMovie(movie, UserSession.getInstance().getLoggedInUser());

                    System.out.println("\n" + movieNameToEdit + " updated successfully!");
                }
                adminMenu();
                break;
            case 3:
                System.out.println();
                movieDao = new MovieDaoImpl();
                List<Movie> movies = movieDao.getAllMovies();
                if (movies.isEmpty()) {
                    System.out.println("No movies found.");
                } else {
                    for (Movie m : movies) {
                        System.out.println(m.getName() + ": " + m.getAverageRating() + " (" + m.getNumRatings() + ")");
                    }
                }
                adminMenu();
                break;
            case 4:
                System.out.println();
                break;
            default:
                adminMenu();
                break;
        }
    }
}


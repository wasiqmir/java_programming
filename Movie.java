import java.util.Scanner;

class Movie {
    String name;
    String director;
    int releaseYear;
    double rating;

    void inputDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Movie Name: ");
        name = sc.nextLine();
        System.out.print("Enter Director: ");
        director = sc.nextLine();
        System.out.print("Enter Release Year: ");
        releaseYear = sc.nextInt();
        System.out.print("Enter Rating: ");
        rating = sc.nextDouble();
    }

    void display() {
        System.out.println("Movie: " + name + ", Director: " + director + ", Year: " + releaseYear + ", Rating: " + rating);
    }

    static void showAboveThreshold(Movie[] movies, double threshold) {
        for (Movie m : movies) {
            if (m.rating > threshold) m.display();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of movies: ");
        int n = sc.nextInt();
        sc.nextLine();
        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            movies[i] = new Movie();
            movies[i].inputDetails();
        }
        for (Movie m : movies) m.display();
        System.out.print("Enter rating threshold: ");
        double t = sc.nextDouble();
        showAboveThreshold(movies, t);
    }
}

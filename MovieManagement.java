package Session17.kha1.presentation;

import Session17.kha1.business.MovieBusiness;
import Session17.kha1.entity.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MovieManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("--- Quan lich phim ---");
            System.out.println("1. Them phim");
            System.out.println("2. Liet ke phim");
            System.out.println("3. Sua phim");
            System.out.println("4. Xoa phim");
            System.out.println("5. Thoat");
            System.out.print("Lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    addMovie(sc);
                    break;
                case 2:
                    displayMovies();
                    break;
                case 3:
                    updateMovie(sc);
                    break;
                case 4:
                    deleteMovie(sc);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.err.println("Vui long nhap tu 1-5");
            }
        } while (true);
    }

    public static void displayMovies() {
        List<Movie> movies = MovieBusiness.findAllMovies();
        movies.forEach(System.out::println);
    }

    public static boolean addMovie(Scanner sc) {
        Movie movie = new Movie();
        movie.input(sc);
        boolean result = MovieBusiness.addMovie(movie.getTitle(), movie.getDirector(), movie.getYear());
        if (result) {
            System.out.println("Them phim thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh add.");
        }
        return result;
    }

    public static void updateMovie(Scanner sc) {
        System.out.println("Nhap id phim can cap nhat:");
        int id = Integer.parseInt(sc.nextLine());
        Movie movie = MovieBusiness.findMovieById(id);
        if (movie == null) {
            System.err.println("Khong tim thay id.");
            return;
        }
        boolean isExit = true;
        do {
            System.out.println("1. Cap nhat tieu de");
            System.out.println("2. Cap nhat dao dien");
            System.out.println("3. Cap nhat nam phat hanh");
            System.out.println("4. Thoat");
            System.out.print("Lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhap tieu de moi cua phim:");
                    movie.setTitle(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Nhap dao dien moi cua phim:");
                    movie.setDirector(sc.nextLine());
                    break;
                case 3:
                    do {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            System.out.println("Nhap nam phat hanh moi (dd-MM-yyyy): ");
                            movie.setYear(LocalDate.parse(sc.nextLine(), formatter));
                            break;
                        } catch (DateTimeParseException e) {
                            System.err.println("Sai dinh dang ngay! Vui long nhap dd-MM-yyyy");
                        }
                    } while (true);
                    break;
                case 4:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui long nhap tu 1-4");
            }
        } while (isExit);
        boolean result = MovieBusiness.updateMovie(id, movie.getTitle(), movie.getDirector(), movie.getYear());
        if (result) {
            System.out.println("Cap nhat thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh update.");
        }
    }

    public static void deleteMovie(Scanner sc) {
        System.out.println("Nhap id phim can xoa:");
        int id = Integer.parseInt(sc.nextLine());
        Movie movie = MovieBusiness.findMovieById(id);
        if (movie == null) {
            System.err.println("Khong tim thay id.");
            return;
        }
        boolean result = MovieBusiness.deleteMovie(id);
        if (result) {
            System.out.println("Xoa phim thanh cong");
        } else {
            System.out.println("Co loi trong qua trinh delete");
        }
    }
}

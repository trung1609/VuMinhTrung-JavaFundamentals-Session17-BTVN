package Session17.kha1.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Movie {
    private int id;
    private String title;
    private String director;
    private LocalDate year;

    public Movie(int id, String title, String director, LocalDate year) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("ID: %d, Title: %s, Director: %s, Year: %s", id, title, director, year.format(dtf));
    }

    public void input(Scanner sc) {
        System.out.println("Nhap tieu de: ");
        this.title = sc.nextLine();
        System.out.println("Nhap dao dien: ");
        this.director = sc.nextLine();
        do {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                System.out.println("Nhap nam phat hanh: ");
                String date = sc.nextLine();
                this.year = LocalDate.parse(date, dtf);
                break;
            }catch (DateTimeParseException e){
                System.err.println("Vui long nhap ngay dung dinh dang.");
            }
        }while (true);
    }
}

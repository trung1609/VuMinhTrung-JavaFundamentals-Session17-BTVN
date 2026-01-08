package Session17.gioi1.presentation;

import Session17.gioi1.business.BookBusiness;
import Session17.gioi1.entity.Book;

import java.util.List;
import java.util.Scanner;

public class BookManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("--- QUAN LY SACH ---");
            System.out.println("1. Them sach");
            System.out.println("2. Cap nhat thong tin sach");
            System.out.println("3. Xoa sach");
            System.out.println("4. Tim kiem theo tac gia");
            System.out.println("5. Hien thi tat ca sach");
            System.out.println("6. Thoat");
            System.out.print("Lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    updateBook(sc);
                    break;
                case 3:
                    deleteBook(sc);
                    break;
                case 4:
                    findBooksByAuthor(sc);
                    break;
                case 5:
                    displayBook();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.err.println("Vui long chon tu 1-6");
            }
        } while (true);
    }

    public static void addBook(Scanner sc) {
        Book book = new Book();
        book.input(sc);
        boolean result = BookBusiness.addBook(book);
        if (result) {
            System.out.println("Them sach thanh cong");
        } else {
            System.err.println("Co loi khi them sach");
        }
    }

    public static void displayBook() {
        List<Book> bookList = BookBusiness.listAllBooks();
        bookList.forEach(System.out::println);
    }

    public static void updateBook(Scanner sc) {
        System.out.println("Nhap id sach can cap nhat: ");
        int id = Integer.parseInt(sc.nextLine());
        Book book = BookBusiness.findBookById(id);
        if (book == null) {
            System.out.println("Khong tim thay id sach");
            return;
        }
        boolean isExist = true;
        do {
            System.out.println("1. Cap nhat tieu de");
            System.out.println("2. Cap nhat tac gia");
            System.out.println("3. Cap nhat nam xuat ban");
            System.out.println("4. Cap nhat gia");
            System.out.println("5. Thoat");
            System.out.print("Lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhap tieu de moi: ");
                    book.setTitle(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Nhap tac gia moi: ");
                    book.setAuthor(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Nhap nam xuat ban moi: ");
                    book.setPublishedYear(Integer.parseInt(sc.nextLine()));
                    break;
                case 4:
                    System.out.println("Nhap gia moi: ");
                    book.setPrice(Double.parseDouble(sc.nextLine()));
                    break;
                case 5:
                    isExist = false;
                    break;
            }
        } while (isExist);
        boolean result = BookBusiness.updateBook(id, book);
        if (result) {
            System.out.println("Cap nhat sach thanh cong");
        } else {
            System.out.println("Co loi khi cap nhat sach");
        }
    }

    public static void deleteBook(Scanner sc) {
        System.out.println("Nhap id sach can xoa: ");
        int id = Integer.parseInt(sc.nextLine());
        Book book = BookBusiness.findBookById(id);
        if (book == null) {
            System.out.println("Khong tim thay id sach");
            return;
        }
        boolean result = BookBusiness.deleteBook(id);
        if (result) {
            System.out.println("Xoa sach thanh cong");
        } else {
            System.out.println("Co loi khi xoa sach");
        }
    }

    public static void findBooksByAuthor(Scanner sc) {
        System.out.println("Nhap ten tac gia can tra cuu: ");
        String author = sc.nextLine();
        List<Book> bookList = BookBusiness.findBooksByAuthor(author);
        bookList.forEach(System.out::println);
    }
}

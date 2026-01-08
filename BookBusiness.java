package Session17.gioi1.business;

import Session17.gioi1.entity.Book;
import Session17.gioi1.utils.ConnectionDB;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BookBusiness {
    public static boolean isBookExist(String title, String author) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call check_book_exist(?,?,?)");
            callSt.setString(1, title);
            callSt.setString(2, author);
            callSt.registerOutParameter(3, Types.BOOLEAN);
            callSt.execute();
            return callSt.getBoolean(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean addBook(Book book) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            if (isBookExist(book.getTitle(), book.getAuthor())) {
                System.err.println("Sach da ton tai (trung ten + tac gia)");
                return false;
            }
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_book(?,?,?,?)");
            callSt.setString(1, book.getTitle());
            callSt.setString(2, book.getAuthor());
            callSt.setInt(3, book.getPublishedYear());
            callSt.setBigDecimal(4, BigDecimal.valueOf(book.getPrice()));
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static List<Book> listAllBooks() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Book> bookList = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call list_all_books()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                bookList = new ArrayList<>();
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishedYear(rs.getInt("published_year"));
                    book.setPrice(rs.getDouble("price"));
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bookList;
    }

    public static Book findBookById(int id) {
        Book book = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_book_by_id(?)}");
            callSt.setInt(1, id);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishedYear(rs.getInt("published_year"));
                    book.setPrice(rs.getDouble("price"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return book;
    }

    public static boolean updateBook(int id, Book book) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_book(?,?,?,?,?)");
            callSt.setInt(1, id);
            callSt.setString(2, book.getTitle());
            callSt.setString(3, book.getAuthor());
            callSt.setInt(4, book.getPublishedYear());
            callSt.setBigDecimal(5, BigDecimal.valueOf(book.getPrice()));
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean deleteBook(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_book(?)");
            callSt.setInt(1, id);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static List<Book> findBooksByAuthor(String author) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Book> bookList = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_books_by_author(?)}");
            callSt.setString(1, author);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                bookList = new ArrayList<>();
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishedYear(rs.getInt("published_year"));
                    book.setPrice(rs.getDouble("price"));
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bookList;
    }
}

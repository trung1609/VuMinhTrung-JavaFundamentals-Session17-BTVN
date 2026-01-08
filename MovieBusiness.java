package Session17.kha1.business;

import Session17.kha1.entity.Movie;
import Session17.kha1.presentation.MovieManagement;
import Session17.kha1.utils.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieBusiness {
    public static boolean addMovie(String title, String director, LocalDate year) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_movie(?,?,?)");
            callSt.setString(1, title);
            callSt.setString(2, director);
            callSt.setDate(3, java.sql.Date.valueOf(year));
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static List<Movie> findAllMovies() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Movie> movies = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call list_movie()}");
            boolean hasData = callSt.execute();
            if (hasData) {
                movies = new ArrayList<>();
                ResultSet rs = callSt.getResultSet();
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDirector(rs.getString("director"));
                    movie.setYear(rs.getDate("year").toLocalDate());
                    movies.add(movie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return movies;
    }

    public static Movie findMovieById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Movie movie = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_movie_by_id(?)}");
            callSt.setInt(1, id);
            boolean hasData = callSt.execute();
            if (hasData) {
                ResultSet rs = callSt.getResultSet();
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDirector(rs.getString("director"));
                    movie.setYear(rs.getDate("year").toLocalDate());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return movie;
    }

    public static boolean updateMovie(int id, String title, String director, LocalDate year) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_movie(?,?,?,?)");
            callSt.setInt(1, id);
            callSt.setString(2, title);
            callSt.setString(3, director);
            callSt.setDate(4, java.sql.Date.valueOf(year));
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static boolean deleteMovie(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_movie(?)");
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
}

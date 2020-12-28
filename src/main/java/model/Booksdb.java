package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The model facade which interacts with the controller and the rest if the classes in
 * the model. Implements the interface "DatabaseMethods"
 *
 * @author sofia och micke
 */
public class Booksdb implements DatabaseMethods{
    private List<Book> books;
    Connection con;

    public Booksdb(){
        books = new ArrayList<Book>();
    }

    /**
     * Connects to the database.
     *
     * @param database the name of the database to connect to
     * @return true if connection is established.
     * @throws SQLException
     */
    @Override
    public boolean connect(String database) throws SQLException{
        if(loadDriver()){
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.1.176:3306/"+database+"?serverTimezone=UTC", "application001", "1234");
            return true;
        }
        return false;
    }

    /**
     * Loads the driver so that communication with the server in SQL is possible
     *
     * @return true if load of JAR-file is successful
     */
    private boolean loadDriver(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            return false;
        }
        return true;
    };

    /**
     * Effectively disconnects from the database
     *
     * @return true if successful
     * @throws SQLException
     */
    @Override
    public boolean disconnect() throws SQLException {
        con.close();
        return true;
    }

    /**
     * Asks database for a list of books with matching title to the string given by user.
     *
     * @param search String of title
     * @return a list of books that matches the serach
     * @throws SQLException
     */
    @Override
    public synchronized List<Book> searchByTitle(String search) throws SQLException {
        if(con!=null){
            books.clear();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(("call searchByTitle('"+search+"')"));
            while(rs.next())
                books.add(new Book(Integer.toString(rs.getInt(1)),rs.getString(2),Genre.valueOf(rs.getString(3).toUpperCase()),rs.getInt(4)));
                stmt.close();
        }
        return books;
    }

    /**
     * Asks database for a list of books with matching ISBN to the string given by user.
     *
     * @param search String of isbn
     * @return a list of books that matches the search
     * @throws SQLException
     */
    @Override
    public synchronized List<Book> searchByISBN(String search) throws SQLException {
        if(con!=null){
            books.clear();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(("call searchByIsbn('"+search+"')"));
            while(rs.next())
                books.add(new Book(Integer.toString(rs.getInt(1)),rs.getString(2),Genre.valueOf(rs.getString(3).toUpperCase()),rs.getInt(4)));
                stmt.close();
        }
        return books;
    }

    /**
     * Asks database for a list of books written by
     * the author provided by user.
     *
     * @param search the name of the author
     * @return a list of books that matches the search
     * @throws SQLException
     */
    @Override
    public synchronized List<Book> searchByAuthor(String search) throws SQLException {
        if(con!=null){
            books.clear();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(("call searchByAuthor('"+search+"')"));
            while(rs.next())
                books.add(new Book(Integer.toString(rs.getInt(1)),rs.getString(2),Genre.valueOf(rs.getString(3).toUpperCase()),rs.getInt(4)));
                stmt.close();
        }
        return books;
    }

    /**
     * Adds a book to the database
     *
     * @param book the book to be added
     * @throws SQLException
     */
    @Override
    public void addBook(Book book) throws SQLException {//todo isbn i databasen måste vara längre
        if(con!=null){
            CallableStatement stmt = con.prepareCall("{call addBook(?,?,?,?)}");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2,book.getTitle());
            stmt.setString(3,book.getGenre().toString());
            stmt.setString(4,Integer.toString(book.getRating()));
            stmt.execute();
            stmt.close();
        }
    }

    /**
     * Adds an author to an already exciting book
     * @param author the author to be added
     * @throws SQLException
     */
    @Override
    public void addAuthorToBook(Author author) throws SQLException  {
        if(con!=null){
            CallableStatement stmt = con.prepareCall("{call addAuthorToBook(?,?,?)}");
            stmt.setString(1,author.getName());
            stmt.setString(2,author.getDateOfBirth());
            stmt.setString(3,author.getIsbn().get(0));
            stmt.execute();
            stmt.close();
        }
    }

    @Override
    public String toString() {
        return "Booksdb{" +
                "books=" + books +
                ", con=" + con +
                '}';
    }
}

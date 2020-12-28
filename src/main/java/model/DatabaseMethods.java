package model;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 *
 * @author sofia och micke
 */
public interface DatabaseMethods {
    /**
     * Connect to the database.
     * @param database the name of the database to connect to
     * @return true on successful connection.
     */
    public abstract boolean connect(String database)throws SQLException;

    /**
     * Disconnects from the database
     *
     * @return true if disconnected
     * @throws SQLException
     */
    public default boolean disconnect()throws SQLException {
        return true;
    }

    /**
     * Searches the database for all books by a certain title
     *
     * @param search the title or substring of a title
     * @return the list of books which matches the search string
     * @throws SQLException
     */
    public List<Book> searchByTitle(String search)throws SQLException;

    /**
     * Searches the database for all books by a certain ISBN
     *
     * @param search the ISBN, must be correct
     * @return
     * @throws SQLException
     */
    public List<Book> searchByISBN(String search)throws SQLException;

    /**
     *  Searches the database for all books written by a certain author.
     *
     * @param search the name of the author
     * @return the list of books
     * @throws SQLException
     */
    public List<Book> searchByAuthor(String search)throws SQLException;

    /**
     * Adds book to collection in database
     *
     * @param book the book to be added
     * @throws SQLException
     */
    public void addBook(Book book) throws SQLException;

    /**
     * Adds author to a specific book.
     *
     * @param author the author to be added
     * @throws SQLException
     */
    public void addAuthorToBook(Author author) throws SQLException;
}

package model;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class that represents a Book
 *
 * @author sofia och micke
 */
public class Book {
    private String isbn;
    private String title;
    private int rating;
    private Genre genre;
    private List<Author> authors;
    private static final Pattern ISBN_PATTERN =
            Pattern.compile("^\\d{9}[\\d|X]$");

    public Book(String isbn,String title, Genre genre, int rating)throws IllegalArgumentException{
        this.title = title;
        if(isValidIsbn(isbn)){
            this.isbn = isbn;
        }else{
            throw new IllegalArgumentException();
        }
        this.rating = rating;
        this.genre = genre;
    }

    /**
     * Checks if the Isbn number is accurately written
     *
     * @param isbn the isbn string
     * @return true if isbn is correct/false if otherwise.
     */
    public static boolean isValidIsbn(String isbn){
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    /**
     * Adds an author to the list
     *
     * @param author the author to be addded
     */
    public void addAuthor (Author author) {
        authors.add(author);
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<Author> getAuthors() {
        List<Author> clone = new ArrayList<Author>(authors);
        return clone;
    }

    /**
     * String property methods to use for Tableview in view.
     * @return a property
     */
    public StringProperty titleProperty(){
        StringProperty titleProperty = new ReadOnlyStringWrapper(title);
        return titleProperty;
    }

    public StringProperty genreProperty(){
        StringProperty genreProperty = new ReadOnlyStringWrapper(genre.toString());
        return genreProperty;
    }

    public StringProperty ratingProperty(){
        StringProperty ratingProperty = new ReadOnlyStringWrapper(Integer.toString(rating));
        return ratingProperty;
    }

    public StringProperty isbnProperty(){
        StringProperty isbnProperty = new ReadOnlyStringWrapper(isbn);
        return isbnProperty;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", genres=" + genre +
                ", isbn=" + isbn +
               // ", athors=" + Arrays.toString(athors) +
                '}';
    }
}

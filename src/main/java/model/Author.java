package model;


import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a book
 *
 * @author sofia och micke
 */
public class Author {
    private String name;
    private String dateOfBirth;
    private List<String> isbn;

    /**
     * Constructor that creates a book object with below parameters.
     *
     * @param name
     * @param dateOfBirth
     * @param isbn
     */
    public Author(String name, String dateOfBirth, String isbn){
        this.isbn = new ArrayList<String>();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        System.out.println(isbn);
        addIsbn(isbn);
    }

    private void addIsbn(String isbn) {
        this.isbn.add(isbn);
    }

    public String getName() {
        return name;
    }

    public List<String> getIsbn() {
        return isbn;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Author{" +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", isbn=" + isbn +
                '}';
    }
}

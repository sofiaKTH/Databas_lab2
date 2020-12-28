package model;

import java.io.IOException;
import java.sql.SQLException;

public class Maintest {
    public static void main(String [] args) throws IOException, SQLException {
        Booksdb booksdb = new Booksdb();
        System.out.println(booksdb.connect("bookdb"));
        booksdb.searchByTitle("test");
    }
}

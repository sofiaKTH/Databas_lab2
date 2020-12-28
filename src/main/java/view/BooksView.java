package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Book;
import model.DatabaseMethods;
import model.Genre;
import model.SearchMode;

import java.util.List;

/**
 * The main pane for the view, extending VBox and including the menus. An
 * internal BorderPane holds the TableView for books and a search utility.
 *
 * @author Sofia och Micke
 */
public class BooksView extends VBox {

    private TableView<Book> booksTable;
    private ObservableList<Book> booksInTable; // the data backing the table view
    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    private Button searchButton;
    private final Controller controller;
    private MenuBar menuBar;

    public BooksView(DatabaseMethods model) {
        controller = new Controller(model,this);
        this.init();
    }

    /**
     * Display a new set of books, e.g. from a database select, in the
     * booksTable table view.
     *
     * @param books the books to display
     */
    public void displayBooks(List<Book> books) {
        booksInTable.clear();
        booksInTable.addAll(books);
    }

    /**
     * Notify user on input error or exceptions.
     *
     * @param msg the message
     * @param type types: INFORMATION, WARNING et c.
     */
    public void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

    private void init() {

        booksInTable = FXCollections.observableArrayList();

        // init views and event handlers
        initBooksTable();
        initSearchView();
        initMenus();

        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(searchModeBox, searchField, searchButton);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(booksTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }

    private void initBooksTable() {
        booksTable = new TableView<>();
        booksTable.setEditable(false); // don't allow user updates (yet)

        // define columns
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> ratingCol = new TableColumn<>("rating");
        TableColumn<Book, Genre> genreCol = new TableColumn<>("Genre");

        booksTable.getColumns().addAll(titleCol,isbnCol,ratingCol,genreCol);
        // give title column some extra space
        titleCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.5));

        // define how to fill data for each cell,
        // get values from Book properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // associate the table view with the data
        booksTable.setItems(booksInTable);
    }

    private void initSearchView() {
        searchField = new TextField();
        searchField.setPromptText("Search for...");
        searchModeBox = new ComboBox<>();
        searchModeBox.getItems().addAll(SearchMode.values());
        searchModeBox.setValue(SearchMode.Title);
        searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent event) -> controller.onSearchSelected(searchField.getText(),searchModeBox.getValue()));
    }

    private void initMenus() {

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction((ActionEvent event) -> controller.onExitSelected());
        MenuItem connectItem = new MenuItem("Connect to Db");
        connectItem.setOnAction((ActionEvent event) -> controller.onConnectSelected());
        MenuItem disconnectItem = new MenuItem("Disconnect");
        disconnectItem.setOnAction((ActionEvent event) -> controller.onDisconnectSelected());
        fileMenu.getItems().addAll(exitItem, connectItem, disconnectItem);

        Menu searchMenu = new Menu("Search");
        MenuItem titleItem = new MenuItem("Title");
        titleItem.setOnAction((ActionEvent event) -> controller.onSearchTitleSelected());
        MenuItem isbnItem = new MenuItem("ISBN");
        isbnItem.setOnAction((ActionEvent event) -> controller.onSearchIsbnSelected());
        MenuItem authorItem = new MenuItem("Author");
        authorItem.setOnAction((ActionEvent event) -> controller.onSearchAuthorSelected());
        searchMenu.getItems().addAll(titleItem, isbnItem, authorItem);

        Menu manageMenu = new Menu("Manage");
        MenuItem addBook = new MenuItem("Add Book");
        addBook.setOnAction((ActionEvent event) -> controller.onAddBookSelected());
        MenuItem addAuthor = new MenuItem("Add Author to book");
        addAuthor.setOnAction((ActionEvent event) -> controller.onAddAuthorSelected());

        /* Ingår ej i labb?
        MenuItem removeItem = new MenuItem("Remove");
        removeItem.setOnAction((ActionEvent event) -> controller.onRemoveSelected());
        MenuItem updateItem = new MenuItem("Update");
        updateItem.setOnAction((ActionEvent event) -> controller.onUpdateSelected());
         */
        manageMenu.getItems().addAll(addBook, addAuthor);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
    }

}

package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import model.Book;
import model.Genre;

/**
 * A form that takes input from user and creates a new Book.
 *
 * @author Sofia och Micke
 */
public class BookDialog extends Dialog<Book> {

    private final TextField titleField = new TextField();
    private final ObservableList<Integer> rating =
            FXCollections.observableArrayList(
                    1,
                    2,
                    3,
                    4,
                    5
            );
    private final ComboBox<Integer> ratingChoice = new ComboBox(rating);
    private final TextField authorField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField dobField = new TextField();
    private final ComboBox<Genre> genreChoice = new ComboBox(FXCollections
            .observableArrayList(Genre.values()));

    public BookDialog() {
       buildAddBookDialog();
    }

    /**
     * Creates a form dialog and presents this to the user.
     * Handles input from user
     */
    public void buildAddBookDialog() {

        this.setTitle("Add a new book");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Title "), 1, 1);
        grid.add(titleField, 2, 1);
        grid.add(new Label("Rating "), 1, 4);
        grid.add(ratingChoice, 2, 4);
        grid.add(new Label("Isbn "), 1, 2);
        grid.add(isbnField, 2, 2);
        grid.add(new Label("Genre "), 1, 3);
        grid.add(genreChoice, 2, 3);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // this callback returns the result from our dialog, via
        // Optional<FooBook> result = dialog.showAndWait();
        // FooBook book = result.get();
        // see DialogExample, line 31-34
        this.setResultConverter(new Callback<>() {
            @Override
            public Book call(ButtonType b) {
                Book result = null;
                if (b == buttonTypeOk) {
                    if (isValidData()) {
                        result = new Book(
                                isbnField.getText(),
                                titleField.getText(),
                                genreChoice.getValue(),
                                ratingChoice.getValue()
                                );
                    }
                }

                clearFormData();
                return result;
            }
        });

        // add an event filter to keep the dialog active if validation fails
        // (yes, this is ugly in FX)
        Button okButton
                = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
        okButton.addEventFilter(ActionEvent.ACTION, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!isValidData()) {
                    event.consume();
                    showErrorAlert("Form error", "Invalid input");
                }
            }
        });
    }

    /**
     * Checks the input data from user
     *
     * @return true if correct
     */
    private boolean isValidData() {

        if (genreChoice.getValue() == null) {
            return false;
        }
        if (!Book.isValidIsbn(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }
        if (ratingChoice.getValue() == null) {
            return false;
        }
        if (titleField.getText() == null) {
            return false;
        }
        return true;
    }

    /**
     * Clears all the variables
     */
    private void clearFormData() {
        titleField.setText("");
        isbnField.setText("");
        genreChoice.setValue(null);
        ratingChoice.setValue(null);
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * Shows error alert if input is incorrect
     * @param title String messege to user
     * @param info which alert
     */
    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}



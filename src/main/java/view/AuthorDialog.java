package view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import model.Author;
import model.Book;

/**
 * A form in a JavaFX Dialog window, which takes input from a user
 * and creates a new author
 *
 * @author sofia och micke
 */
public class AuthorDialog extends Dialog<Author> {

    private final TextField authorField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField dobField = new TextField();

    public AuthorDialog() {
        buildAddAuthorDialog();
    }

    /**
     * Creates and displays the dialog form to the user.
     * The form handles input values and creates an author.
     */
    public void buildAddAuthorDialog() {

        this.setTitle("Add a new Author");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Name "), 1, 1);
        grid.add(authorField, 2, 1);
        grid.add(new Label("Date of birth yy-mm-dd "), 1, 4);
        grid.add(dobField, 2, 4);
        grid.add(new Label("Isbn "), 1, 2);
        grid.add(isbnField, 2, 2);


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
        this.setResultConverter(new Callback<ButtonType, Author>() {
            @Override
            public Author call(ButtonType b) {
                Author result = null;
                if (b == buttonTypeOk) {
                    if (isValidData()) {
                        System.out.println(authorField.getText() +"  " +
                                dobField.getText() + "  " +
                                isbnField.getText());
                        result = new Author(
                                authorField.getText(),
                                dobField.getText(),
                                isbnField.getText()
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
     * Checks and controls the input data from user
     * @return true if correct data
     */
    private boolean isValidData() {
        if (authorField.getText() == null) {
            return false;
        }
        if (!Book.isValidIsbn(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }
        if (dobField.getText() == null) {
            return false;
        }
        return true;
    }

    /**
     * Clears all variables
     */
    private void clearFormData() {
        dobField.setText("");
        isbnField.setText("");
        authorField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * Shows error alert if data is not valid
     * @param title
     * @param info
     */
    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}

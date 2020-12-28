package view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class SearchDialog extends Dialog<String> {
    private String whichSearch;
    private ButtonType buttonTypeOk;
    private ButtonType buttonTypeCancel;
    private GridPane grid;
    private final TextField textInput = new TextField();
    private final TextField authorField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField titleField = new TextField();

    public SearchDialog(String s) {
        whichSearch = s;
        this.showSearchDialog();

    }
    private void showSearchDialog() {
        clearFormData();
        buttonTypeOk = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        int help =0;

        if (whichSearch.equalsIgnoreCase("title")) {
            this.setTitle("Title");
            this.setHeaderText("Enter Title");
            grid.add(titleField, 2, 1);
            help =1;
            this.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    String result = null;
                    if (b == buttonTypeOk) {
                        if (isValidTitle()) {
                            result = titleField.getText();
                        }
                    }
                    clearFormData();
                    return result;
                }
            });
        }
        else if (whichSearch.equalsIgnoreCase("isbn")) {
            this.setTitle("ISBN");
            this.setHeaderText("Enter isbn");
            grid.add(isbnField, 2, 1);
            help =2;
            this.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    String result = null;
                    if (b == buttonTypeOk) {
                        if (isValidIsbn()) {
                            result = isbnField.getText();
                        }
                    }
                    clearFormData();
                    return result;
                }
            });

        }
        else if (whichSearch.equalsIgnoreCase("author")) {
            this.setTitle("Author");
            this.setHeaderText("Enter name of author");
            grid.add(authorField, 2, 1);
            help =3;
            this.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    String result = null;
                    if (b == buttonTypeOk) {
                        if (isValidAuthor()) {
                            result = authorField.getText();
                        }
                    }
                    clearFormData();
                    return result;
                }
            });
        }

        Button okButton
                = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
        int finalHelp = help;
        okButton.addEventFilter(ActionEvent.ACTION, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!isValidData(finalHelp)) {
                    event.consume();
                    showErrorAlert("Form error", "Invalid input");
                }
            }
        });
    }

    private boolean isValidData(int n) {

        if(n == 1) {
            return isValidTitle();
        }
        if (n == 2) {
            return isValidIsbn();
        }
        return isValidAuthor();
    }


    private boolean isValidAuthor() {
        if (authorField.getText() == null) {
            return false;
        }
        return true;
    }

    private boolean isValidIsbn() {
        if (isbnField.getText() != null) {
            return true;
            // TODO: add a checker for isbn. How? There is a checker in Book but we havent created a book here
        }
        return false;
    }

    private boolean isValidTitle() {

        if (titleField.getText() == null) {
            return false;
        }
      return true;
    }

    private void clearFormData() {
        titleField.setText(null);
        isbnField.setText(null);
        authorField.setText(null);
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}

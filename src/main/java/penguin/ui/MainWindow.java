package penguin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import penguin.Penguin;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Penguin penguin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/koala.png"));
    private Image penguinImage = new Image(this.getClass().getResourceAsStream("/images/penguin.png"));

    private String commandType = "";

    private final String BYE = "Bye. Hope to see you again soon!";

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().addAll(DialogBox.getPenguinDialog(Ui.greet(), penguinImage, commandType));
    }

    /** Injects the penguin instance */
    public void setPenguin(Penguin penguin) {
        this.penguin = penguin;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = penguin.getResponse(input);
        commandType = penguin.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPenguinDialog(response, penguinImage, commandType)
        );
        userInput.clear();

        if ("bye".equals(input)) {
            String reply = Ui.reply(BYE);
            dialogContainer.getChildren().add(new Label(reply));
        }
    }
}
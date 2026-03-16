package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-w09-3.github.io/tp/UserGuide.html";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private VBox helpContentBox;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        buildHelpContent();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Builds the styled help content as structured command blocks.
     */
    private void buildHelpContent() {
        Label heading = new Label("Command Summary");
        heading.getStyleClass().add("help-heading");

        Separator headingSeparator = new Separator();
        headingSeparator.getStyleClass().add("help-separator");

        helpContentBox.getChildren().addAll(heading, headingSeparator);

        addCommandBlock("add", "Add a new contact",
                "add n/NAME p/PHONE e/EMAIL a/ADDRESS",
                "add n/John Doe p/98765432 e/johnd@example.com a/123, Main St");

        addCommandBlock("delete", "Remove a contact by index",
                "delete INDEX",
                "delete 3");

        addCommandBlock("edit", "Edit an existing contact",
                "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]",
                "edit 3 n/Jane Smith p/98765433");

        addCommandBlock("find", "Search contacts by keyword",
                "find KEYWORD [MORE_KEYWORDS]",
                "find John");

        addCommandBlock("list", "List all contacts",
                "list",
                "list");

        addCommandBlock("clear", "Clear all entries",
                "clear",
                "clear");

        addCommandBlock("help", "Show this help window",
                "help",
                "help");

        addCommandBlock("exit", "Exit the application",
                "exit",
                "exit");

        Separator footerSeparator = new Separator();
        footerSeparator.getStyleClass().add("help-separator");

        Label footer = new Label("For full details, click the button below to copy the URL to the user guide!");
        footer.getStyleClass().add("help-footer");
        footer.setWrapText(true);

        helpContentBox.getChildren().addAll(footerSeparator, footer);
    }

    private void addCommandBlock(String command, String description, String format, String example) {
        VBox block = new VBox(4);
        block.getStyleClass().add("help-block");

        HBox commandRow = new HBox(8);
        commandRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label cmdLabel = new Label(command);
        cmdLabel.getStyleClass().add("help-command");
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("help-description");
        commandRow.getChildren().addAll(cmdLabel, descLabel);

        Label formatLabel = new Label("Format:  " + format);
        formatLabel.getStyleClass().add("help-format");
        formatLabel.setWrapText(true);
        VBox.setMargin(formatLabel, new Insets(0, 0, 0, 10));

        Label exampleLabel = new Label("Example: " + example);
        exampleLabel.getStyleClass().add("help-example");
        exampleLabel.setWrapText(true);
        VBox.setMargin(exampleLabel, new Insets(0, 0, 0, 10));

        block.getChildren().addAll(commandRow, formatLabel, exampleLabel);
        helpContentBox.getChildren().add(block);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}

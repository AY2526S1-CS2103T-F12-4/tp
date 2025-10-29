package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindDoctorCommand;
import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.commands.MedCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final String SUCCESS_STYLE_CLASS = "success";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    @FXML
    private Label commandHint;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            updateCommandHint();
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Updates the command hint based on current input.
     */
    private void updateCommandHint() {
        String text = commandTextField.getText().toLowerCase().trim();

        if (text.isEmpty()) {
            commandHint.setText("Type 'help' for available commands");
            return;
        }

        // Define known commands with their usage strings
        String[][] commands = new String[][]{
                {AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE},
                {EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE},
                {DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE},
                // placed before 'find' to avoid shadowing
                {FindMedicineCommand.COMMAND_WORD, FindMedicineCommand.MESSAGE_USAGE},
                {FindDoctorCommand.COMMAND_WORD, FindDoctorCommand.MESSAGE_USAGE},
                {FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE},
                {ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_USAGE},
                {MedCommand.COMMAND_WORD, MedCommand.MESSAGE_USAGE},
                {LogCommand.COMMAND_WORD, LogCommand.MESSAGE_USAGE},
                {DisplayCommand.COMMAND_WORD, DisplayCommand.MESSAGE_USAGE},
                {HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE},
                {ListCommand.COMMAND_WORD, "List all patients: " + ListCommand.COMMAND_WORD},
                {ClearCommand.COMMAND_WORD, "Clear all patients: " + ClearCommand.COMMAND_WORD},
                {ExitCommand.COMMAND_WORD, "Exit the application: " + ExitCommand.COMMAND_WORD}
        };

        // 1) Exact match takes priority (handles find vs findmed conflict when full word typed)
        for (String[] entry : commands) {
            if (entry[0].equals(text)) {
                commandHint.setText(entry[1]);
                return;
            }
        }

        // 2) Partial match: any command whose word starts with the typed text
        java.util.List<String[]> matches = new java.util.ArrayList<>();
        for (String[] entry : commands) {
            if (entry[0].startsWith(text)) {
                matches.add(entry);
            }
        }

        if (matches.size() == 1) {
            commandHint.setText(matches.get(0)[1]);
            return;
        }

        if (matches.size() > 1) {
            // Show possible commands to guide further typing
            String suggestions = matches.stream()
                    .map(e -> e[0])
                    .sorted()
                    .limit(5)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            commandHint.setText("Possible commands: " + suggestions);
            return;
        }

        commandHint.setText("Unknown command. Type 'help' for available commands");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        commandTextField.getStyleClass().remove(SUCCESS_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        styleClass.remove(SUCCESS_STYLE_CLASS);

        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        styleClass.remove(ERROR_STYLE_CLASS);

        if (!styleClass.contains(SUCCESS_STYLE_CLASS)) {
            styleClass.add(SUCCESS_STYLE_CLASS);
        }

        // Remove success style after a short delay
        javafx.application.Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
                javafx.application.Platform.runLater(() -> setStyleToDefault());
            } catch (InterruptedException e) {
                // Ignore interruption
            }
        });
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

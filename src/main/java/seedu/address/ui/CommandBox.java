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
        } else if (text.startsWith(AddCommand.COMMAND_WORD)) {
            commandHint.setText(AddCommand.MESSAGE_USAGE);
        } else if (text.startsWith(DeleteCommand.COMMAND_WORD)) {
            commandHint.setText(DeleteCommand.MESSAGE_USAGE);
        } else if (text.startsWith(EditCommand.COMMAND_WORD)) {
            commandHint.setText(EditCommand.MESSAGE_USAGE);
        } else if (text.startsWith(FindCommand.COMMAND_WORD)) {
            commandHint.setText(FindCommand.MESSAGE_USAGE);
        } else if (text.startsWith(FindMedicineCommand.COMMAND_WORD)) {
            commandHint.setText(FindMedicineCommand.MESSAGE_USAGE);
        } else if (text.startsWith(FindDoctorCommand.COMMAND_WORD)) {
            commandHint.setText(FindDoctorCommand.MESSAGE_USAGE);
        } else if (text.startsWith(ListCommand.COMMAND_WORD)) {
            commandHint.setText("List all patients: " + ListCommand.COMMAND_WORD);
        } else if (text.startsWith(HelpCommand.COMMAND_WORD)) {
            commandHint.setText(HelpCommand.MESSAGE_USAGE);
        } else if (text.startsWith(ClearCommand.COMMAND_WORD)) {
            commandHint.setText("Clear all patients: " + ClearCommand.COMMAND_WORD);
        } else if (text.startsWith(ExitCommand.COMMAND_WORD)) {
            commandHint.setText("Exit the application: " + ExitCommand.COMMAND_WORD);
        } else if (text.startsWith(ViewCommand.COMMAND_WORD)) {
            commandHint.setText(ViewCommand.MESSAGE_USAGE);
        } else if (text.startsWith(MedCommand.COMMAND_WORD)) {
            commandHint.setText(MedCommand.MESSAGE_USAGE);
        } else if (text.startsWith(LogCommand.COMMAND_WORD)) {
            commandHint.setText(LogCommand.MESSAGE_USAGE);
        } else if (text.startsWith(DisplayCommand.COMMAND_WORD)) {
            commandHint.setText(DisplayCommand.MESSAGE_USAGE);
        } else {
            commandHint.setText("Unknown command. Type 'help' for available commands");
        }
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

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Displays the visit dates (DayList) for a person in the address book.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the visit dates for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DISPLAY_PERSON_SUCCESS = "Visit dates for %1$s:\n%2$s";
    public static final String MESSAGE_NO_VISITS = "No visits recorded for %1$s";

    private final Index targetIndex;

    /**
     * Creates a DisplayCommand to show visit dates for the specified person.
     */
    public DisplayCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDisplay = lastShownList.get(targetIndex.getZeroBased());

        if (personToDisplay.getDayList().getVisitCount() == 0) {
            return new CommandResult(String.format(MESSAGE_NO_VISITS, personToDisplay.getName()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String visitDates = personToDisplay.getDayList().getVisitDates().stream()
                .map(date -> "• " + date.format(formatter))
                .reduce((date1, date2) -> date1 + "\n" + date2)
                .orElse("");

        return new CommandResult(String.format(MESSAGE_DISPLAY_PERSON_SUCCESS,
                personToDisplay.getName(), visitDates));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayCommand)) {
            return false;
        }

        DisplayCommand otherDisplayCommand = (DisplayCommand) other;
        return targetIndex.equals(otherDisplayCommand.targetIndex);
    }
}

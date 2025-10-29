package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Displays the visit dates (DayList) for a patient in the address book.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the visit dates for the patient identified "
            + "by the index number used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DISPLAY_PERSON_SUCCESS = "Visit dates for %1$s:\n%2$s";
    public static final String MESSAGE_NO_VISITS = "No visits recorded for %1$s";

    private static final Logger logger = LogsCenter.getLogger(DisplayCommand.class);

    private final Index targetIndex;

    /**
     * Creates a DisplayCommand to show visit dates for the specified patient.
     */
    public DisplayCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        logger.info("Executing DisplayCommand for index: " + targetIndex.getOneBased());

        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list should not be null";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid person index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDisplay = lastShownList.get(targetIndex.getZeroBased());
        assert personToDisplay != null : "Person to display should not be null";
        assert personToDisplay.getDayList() != null : "Person's DayList should not be null";
        logger.info("Displaying visit dates for person: " + personToDisplay.getName());

        if (personToDisplay.getDayList().getVisitCount() == 0) {
            logger.info("No visits found for person: " + personToDisplay.getName());
            return new CommandResult(String.format(MESSAGE_NO_VISITS, personToDisplay.getName()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        assert formatter != null : "DateTimeFormatter should not be null";
        String visitDates = personToDisplay.getDayList().getVisitDates().stream()
                .map(date -> "• " + date.format(formatter))
                .reduce((date1, date2) -> date1 + "\n" + date2)
                .orElse("");

        assert !visitDates.isEmpty() : "Visit dates string should not be empty when there are visits";
        logger.info("Successfully displayed " + personToDisplay.getDayList().getVisitCount()
                + " visit dates for person: " + personToDisplay.getName());
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

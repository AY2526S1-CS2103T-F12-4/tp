package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Logs a visit date for a person identified using it's displayed index from the address book.
 */
public class LogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs today's date as a visit for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOG_PERSON_SUCCESS = "Logged visit for person: %1$s";
    public static final String MESSAGE_ALREADY_LOGGED_TODAY = "Visit already logged for today";

    private final Index targetIndex;

    public LogCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToLog = lastShownList.get(targetIndex.getZeroBased());
        LocalDate today = LocalDate.now();

        // Check if today's date is already logged
        if (personToLog.getDayList().hasVisitDate(today)) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_TODAY);
        }

        // Create a new person with today's date added to their DayList
        Person updatedPerson = createPersonWithNewVisitDate(personToLog, today);
        model.setPerson(personToLog, updatedPerson);

        return new CommandResult(String.format(MESSAGE_LOG_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    /**
     * Creates a new Person with the given visit date added to their DayList.
     */
    private Person createPersonWithNewVisitDate(Person person, LocalDate visitDate) {
        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getDoctor(),
                person.getTags(),
                person.getMedicines(),
                person.getDayList().addVisitDate(visitDate)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogCommand)) {
            return false;
        }

        LogCommand otherLogCommand = (LogCommand) other;
        return targetIndex.equals(otherLogCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

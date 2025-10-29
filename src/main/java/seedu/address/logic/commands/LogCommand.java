package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Logs a visit date for a patient identified using it's displayed index from the address book.
 */
public class LogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs today's date as a visit for the patient identified "
            + "by the index number used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOG_PERSON_SUCCESS = "Logged visit for patient: %1$s";
    public static final String MESSAGE_ALREADY_LOGGED_TODAY = "Visit already logged for today";

    private static final Logger logger = LogsCenter.getLogger(LogCommand.class);

    private final Index targetIndex;

    public LogCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        logger.info("Executing LogCommand for index: " + targetIndex.getOneBased());

        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list should not be null";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid person index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToLog = lastShownList.get(targetIndex.getZeroBased());
        assert personToLog != null : "Person to log should not be null";
        LocalDate today = LocalDate.now();
        assert today != null : "Today's date should not be null";
        logger.info("Logging visit for person: " + personToLog.getName() + " on date: " + today);

        // Check if today's date is already logged
        if (personToLog.getDayList().hasVisitDate(today)) {
            logger.info("Visit already logged for today for person: " + personToLog.getName());
            throw new CommandException(MESSAGE_ALREADY_LOGGED_TODAY);
        }

        // Create a new person with today's date added to their DayList
        Person updatedPerson = createPersonWithNewVisitDate(personToLog, today);
        assert updatedPerson != null : "Updated person should not be null";
        assert updatedPerson.getDayList().hasVisitDate(today) : "Updated person should have today's date in DayList";
        model.setPerson(personToLog, updatedPerson);
        logger.info("Successfully logged visit for person: " + updatedPerson.getName());

        return new CommandResult(String.format(MESSAGE_LOG_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    /**
     * Creates a new Person with the given visit date added to their DayList.
     */
    private Person createPersonWithNewVisitDate(Person person, LocalDate visitDate) {
        assert person != null : "Person should not be null";
        assert visitDate != null : "Visit date should not be null";
        assert !person.getDayList().hasVisitDate(visitDate) : "Person should not already have this visit date";

        Person updatedPerson = new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getDoctor(),
                person.getTags(),
                person.getMedicines(),
                person.getDayList().addVisitDate(visitDate)
        );

        assert updatedPerson.getDayList().hasVisitDate(visitDate) : "Updated person should have the new visit date";
        return updatedPerson;
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

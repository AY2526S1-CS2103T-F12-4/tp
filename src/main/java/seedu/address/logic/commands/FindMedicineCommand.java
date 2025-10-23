package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.MedicineContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose medicines contain any of the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class FindMedicineCommand extends Command {

    public static final String COMMAND_WORD = "findmed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose medicines contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " paracetamol ibuprofen";

    private final MedicineContainsKeywordsPredicate predicate;

    private static final Logger logger = LogsCenter.getLogger(FindMedicineCommand.class);

    public FindMedicineCommand(MedicineContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.fine("[FindMedicineCommand] Filtering suing keywords(medicines): " + predicate);
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindMedicineCommand)) {
            return false;
        }
        FindMedicineCommand otherCommand = (FindMedicineCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicate", predicate).toString();
    }
}

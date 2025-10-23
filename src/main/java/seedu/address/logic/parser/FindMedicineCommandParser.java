package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedicineContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindMedicineCommand object
 */
public class FindMedicineCommandParser implements Parser<FindMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a FindMedicineCommand
     * object for execution.
     *
     * Usage: findmed paracetamol ibuprofen
     *
     * @throws ParseException if the user input does not follow the expected format
     */
    @Override
    public FindMedicineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedicineCommand.MESSAGE_USAGE));
        }

        List<String> keywords;

        if (trimmedArgs.equals("none")) {
            keywords = List.of();
        } else {
            keywords = Arrays.stream(trimmedArgs.split("\\s+"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }

        return new FindMedicineCommand(new MedicineContainsKeywordsPredicate(keywords));
    }
}

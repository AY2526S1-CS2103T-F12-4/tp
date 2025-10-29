package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedicineContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindMedicineCommand object
 * Accepts multiple med/ prefixes, e.g. "findmed med/paracetamol med/ibuprofen".
 */
public class FindMedicineCommandParser implements Parser<FindMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a FindMedicineCommand
     * object for execution.
     *
     * Usage: findmed med/paracetamol med/ibuprofen
     *
     * @throws ParseException if the user input does not follow the expected format
     */
    @Override
    public FindMedicineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // prepending a space for argument tokenizer to work
        String updatedArgs = " " + trimmedArgs;
        // Check for "none" keyword
        if (trimmedArgs.equalsIgnoreCase(FindMedicineCommand.NONE_KEYWORD)) {
            return new FindMedicineCommand(new MedicineContainsKeywordsPredicate(List.of()));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(updatedArgs, PREFIX_MEDICINE);

        List<String> keywords = argMultimap.getAllValues(PREFIX_MEDICINE)
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (keywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedicineCommand.MESSAGE_USAGE));
        }
        return new FindMedicineCommand(new MedicineContainsKeywordsPredicate(keywords));
    }
}

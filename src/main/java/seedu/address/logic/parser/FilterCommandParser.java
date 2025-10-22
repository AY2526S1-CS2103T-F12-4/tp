package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DoctorContainsKeywordsPredicate;

import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * Expected usage:
     *  - filter d/James Warner
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException{
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DOCTOR);

        if (argMultimap.getPreamble().trim().length() > 0 || argMultimap.getValue(PREFIX_DOCTOR).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DOCTOR);

        String doctorRaw = argMultimap.getValue(PREFIX_DOCTOR).get().trim();
        if (doctorRaw.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        List<String> doctorKeywords = List.of(doctorRaw.split("\\s+"));

        return new FilterCommand(new DoctorContainsKeywordsPredicate(doctorKeywords));
    }


}

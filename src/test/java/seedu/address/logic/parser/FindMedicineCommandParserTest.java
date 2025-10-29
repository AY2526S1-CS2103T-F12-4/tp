package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.model.person.MedicineContainsKeywordsPredicate;

public class FindMedicineCommandParserTest {

    private FindMedicineCommandParser parser = new FindMedicineCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindMedicineCommand expectedFindCommand =
                new FindMedicineCommand(new MedicineContainsKeywordsPredicate(
                        List.of("paracetamol", "ibuprofen")));
        assertParseSuccess(parser, "med/paracetamol med/ibuprofen", expectedFindCommand);

        //has multiple whitespaces between keywords
        assertParseSuccess(parser, " med/paracetamol  med/ibuprofen  ", expectedFindCommand);
    }

    @Test
    public void parse_noneKeyword_returnsFindCommand() {
        FindMedicineCommand expectedFindCommand =
                new FindMedicineCommand(new MedicineContainsKeywordsPredicate(
                        List.of()));
        assertParseSuccess(parser, "none", expectedFindCommand);

        //has whitespace before and after
        assertParseSuccess(parser, " none ", expectedFindCommand);
    }
}


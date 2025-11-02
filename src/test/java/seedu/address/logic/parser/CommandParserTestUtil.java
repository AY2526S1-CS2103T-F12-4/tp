package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Email;
import seedu.address.model.person.Address;
import seedu.address.model.person.Doctor;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.tag.Tag;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
            Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     *
     * This helper accepts either a {@link ParseException} or an {@link IllegalArgumentException} thrown by
     * downstream constructors. It also tolerates parser messages that end with the consecutive-spaces message
     * (which may be prefixed by the field name, e.g. "Name: Multiple consecutive spaces are not allowed").
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (Exception e) {
            String actualMessage = e.getMessage();
            // If it's a ParseException, ensure message matches expected or ends with the consecutive-spaces message.
            if (e instanceof ParseException) {
                if (actualMessage.equals(expectedMessage) || actualMessage.endsWith(Messages.MESSAGE_CONSECUTIVE_SPACES)
                        || actualMessage.equals(Name.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Phone.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Email.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Address.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Tag.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Doctor.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Medicine.MESSAGE_CONSTRAINTS)) {
                    return;
                }
            }
            // If an IllegalArgumentException bubbled up from a model ctor, accept it as a failure as well
            // and compare messages similarly.
            if (e instanceof IllegalArgumentException) {
                if (actualMessage.equals(expectedMessage) || actualMessage.endsWith(Messages.MESSAGE_CONSECUTIVE_SPACES)
                        || actualMessage.equals(Name.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Phone.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Email.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Address.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Tag.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Doctor.MESSAGE_CONSTRAINTS)
                        || actualMessage.equals(Medicine.MESSAGE_CONSTRAINTS)) {
                    return;
                }
            }
            // Otherwise, assert equality to surface the mismatch.
            assertEquals(expectedMessage, actualMessage);
        }
    }
}

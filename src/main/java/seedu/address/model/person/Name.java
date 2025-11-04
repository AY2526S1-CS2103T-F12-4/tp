package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
        "Names cannot be blank and can contain alphanumeric characters, spaces, commas, "
        + "the at symbol (@), and hyphens. The only allowed slash tokens are 'd/o' and "
        + "'s/o', which must appear as standalone words (surrounded by spaces or at the "
        + "start/end).";

    // One-regex approach: sequence of tokens separated by spaces; each token is either
    //  - alphanumerics with optional @ , - characters, or
    //  - exactly "d/o" or "s/o".
    public static final String VALIDATION_REGEX =
            "^(?!\\s*$)\\s*(?:[A-Za-z0-9@,\\-]+|d/o|s/o)(?:\\s+(?:[A-Za-z0-9@,\\-]+|d/o|s/o))*\\s*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

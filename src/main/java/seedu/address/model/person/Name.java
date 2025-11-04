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

    // Base allowed characters. Slash is included but further restricted in isValidName.
    public static final String VALIDATION_REGEX = "^(?!\\s*$)[A-Za-z0-9 @,\\-/]+$";

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
        if (test == null) {
            return false;
        }

        // Quick character-level validation (allows slash for subsequent token checks)
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        // Enforce that any slash appears only in standalone tokens "d/o" or "s/o"
        String trimmed = test.trim();
        if (trimmed.isEmpty()) {
            return false;
        }

        String[] tokens = trimmed.split("\\s+");
        for (String token : tokens) {
            if (token.indexOf('/') >= 0) {
                if (!token.equals("d/o") && !token.equals("s/o")) {
                    return false;
                }
            }
        }
        return true;
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

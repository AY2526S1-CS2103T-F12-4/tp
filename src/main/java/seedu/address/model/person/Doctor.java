package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's doctor in the address book.
 * Guarantees: immutable; is always valid
 */

public class Doctor {

    public static final String MESSAGE_CONSTRAINTS =
            "Doctor names can be empty or contain alphanumeric characters, spaces, commas, "
                    + "the at symbol (@), hyphens (-), and periods (.). The only allowed slash "
                    + "tokens are 'd/o' and 's/o' as standalone words.";

    // Empty string allowed; otherwise sequence of valid tokens separated by spaces. Each token is either:
    // - [A-Za-z0-9@,.-]+ OR exactly 'd/o' OR exactly 's/o'
    public static final String VALIDATION_REGEX =
            "^$|^(?!\\s*$)(?:[A-Za-z0-9@,.-]+|d/o|s/o)(?:\\s+(?:[A-Za-z0-9@,.-]+|d/o|s/o))*$";

    public final String name;

    /**
     * Constructs an {@code Doctor}.
     *
     * @param doctor A doctor's name.
     */
    public Doctor(String doctor) {
        requireNonNull(doctor);
        checkArgument(isValidDoctor(doctor), MESSAGE_CONSTRAINTS);
        name = doctor;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // if same object, then true
                || (other instanceof Doctor// instanceof handles nulls
                && name.equals(((Doctor) other).name)); // state check
    }

    /**
     * Returns true if a given string is a valid name for a doctor.
     */
    public static boolean isValidDoctor(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}


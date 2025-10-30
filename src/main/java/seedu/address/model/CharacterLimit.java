package seedu.address.model;
/**
 * Contains character limits used for validations for model classes.
 */
public class CharacterLimit {
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 20;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_ADDRESS_LENGTH = 100;
    public static final int MAX_MEDICINE_LENGTH = 60;
    public static final int MAX_TAG_LENGTH = 50;
    public static final int MAX_DOCTOR_LENGTH = 100;

    public static final String MESSAGE_LENGTH_EXCEEDED =
            "%s cannot exceed %d characters";
}


package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Doctor} matches any of the keywords received.
 */
public class DoctorContainsKeywordsPredicate implements Predicate<Person>{
    private final List<String> keywords;

    public DoctorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean test(Person person) {
        // The current implementation returns all patients
        // with doctor names whose part can be found in the
        // doctor name user inputted, similar to find
        String doctorName = person.getDoctor().toString();
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(doctorName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DoctorContainsKeywordsPredicate)) {
            return false;
        }
        DoctorContainsKeywordsPredicate otherPredicate = (DoctorContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}

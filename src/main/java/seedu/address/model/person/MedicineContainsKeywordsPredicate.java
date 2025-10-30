package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.medicine.Medicine;

/**
 * Tests that a {@code Person}'s medicines contain any of the keywords given or whether
 * no medicines are prescribed to that person.
 */
public class MedicineContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public MedicineContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // keywords list is empty when "none" is specified after "findmed"
        if (keywords.isEmpty()) {
            return person.getMedicines().isEmpty();
        }
        // true if any medicine's name matches any keyword
        return person.getMedicines().stream()
                .map(Medicine::toString)
                .anyMatch(medName -> keywords.stream()
                        .anyMatch(keyword -> medName.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MedicineContainsKeywordsPredicate)) {
            return false;
        }
        MedicineContainsKeywordsPredicate otherPredicate = (MedicineContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

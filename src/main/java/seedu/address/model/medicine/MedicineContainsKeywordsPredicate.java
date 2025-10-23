package seedu.address.model.medicine;

import seedu.address.model.person.Person;

import java.util.function.Predicate;

public class MedicineContainsKeywordsPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return false;
    }
}

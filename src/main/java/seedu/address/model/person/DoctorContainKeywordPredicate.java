package seedu.address.model.person;

import java.util.function.Predicate;

public class DoctorContainKeywordPredicate implements Predicate<Person>{

    public boolean test(Person person) {
        return true;
    }

}

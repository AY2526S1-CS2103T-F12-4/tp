package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedicineContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindMedicineCommand}.
 */
public class FindMedicineCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        MedicineContainsKeywordsPredicate firstPredicate =
                new MedicineContainsKeywordsPredicate(Collections.singletonList("paracetamol"));
        MedicineContainsKeywordsPredicate secondPredicate =
                new MedicineContainsKeywordsPredicate(Collections.singletonList("ibuprofen"));

        FindMedicineCommand findFirstCommand = new FindMedicineCommand(firstPredicate);
        FindMedicineCommand findSecondCommand = new FindMedicineCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMedicineCommand findFirstCommandCopy = new FindMedicineCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_unknownKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        MedicineContainsKeywordsPredicate predicate = preparePredicate("abcd");
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        MedicineContainsKeywordsPredicate predicate = preparePredicate("paracetamol ibuprofen aspirin");
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_noneKeyword_findsPatientsWithNoMedicines() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        MedicineContainsKeywordsPredicate predicate =
                new MedicineContainsKeywordsPredicate(Collections.emptyList());
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_findsPatientsWithMatchingMedicine() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        MedicineContainsKeywordsPredicate predicate = preparePredicate("Robitussin");
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code MedicineContainsKeywordsPredicate}.
     */
    private MedicineContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MedicineContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

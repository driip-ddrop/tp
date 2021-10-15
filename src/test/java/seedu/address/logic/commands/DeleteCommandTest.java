package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2106;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtMultipleIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_HENRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_ISAAC;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleCodesContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        //Deletes 1 person
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete);
        expectedMessage = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 1) + expectedMessage;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validRangeUnfilteredList_success() {
        //Deletes 2 persons
        Person personToDelete1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDelete2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage1 = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 2)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete1)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete2);

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel1.deletePerson(personToDelete1);
        expectedModel1.deletePerson(personToDelete2);

        assertCommandSuccess(deleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validModuleCodeUnfilteredList_success() {
        Person person1 = model.getFilteredPersonList().get(INDEX_HENRY.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_ISAAC.getZeroBased());
        Person newPerson2 = new PersonBuilder(person2).withModuleCodes(VALID_MODULE_CODE_CS2106).build();

        DeleteCommand deleteCommand = new DeleteCommand(new ModuleCodesContainsKeywordsPredicate(
                Arrays.asList(String.format("[%s]", VALID_MODULE_CODE_CS2100))),
                new ModuleCode(VALID_MODULE_CODE_CS2100));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 1)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, person1)
                + String.format(DeleteCommand.MESSAGE_NUMBER_EDITED_PERSON, 1)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, person2);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(person1);
        expectedModel.setPerson(person2, newPerson2);
        System.out.println(expectedModel.getFilteredPersonList().get(7));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRangeUnfilteredList_throwsCommandException() {
        Index invalidStartIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 2);
        Index invalidEndIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 4);
        DeleteCommand deleteCommand1 = new DeleteCommand(invalidStartIndex, invalidEndIndex);

        assertCommandFailure(deleteCommand1, model, Messages.MESSAGE_INVALID_RANGE);
    }

    @Test
    public void execute_invalidModuleCodeUnfilteredList_throwsCommandException() {
        ModuleCodesContainsKeywordsPredicate predicate = new ModuleCodesContainsKeywordsPredicate(
                Arrays.asList(String.format("[CS1231]")));
        DeleteCommand deleteCommand = new DeleteCommand(predicate, new ModuleCode("CS1231"));

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_SUCH_MODULE_CODE);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        //Deletes 1 person in the filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete);
        expectedMessage = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 1) + expectedMessage;
        System.out.println(expectedMessage);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validRangeFilteredList_success() {
        //Deletes 2 person in the filtered list
        showPersonAtMultipleIndex(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Person personToDelete1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDelete2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage1 = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 2)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete1)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, personToDelete2);

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel1.deletePerson(personToDelete1);
        expectedModel1.deletePerson(personToDelete2);

        assertCommandSuccess(deleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validModuleCodeFilteredList_success() {
        //Deletes 0 persons in filtered list, only deletes module code
        showPersonAtMultipleIndex(model, INDEX_FIRST_PERSON, INDEX_ISAAC);
        Person person = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person newPerson = new PersonBuilder(person).withModuleCodes(VALID_MODULE_CODE_CS2100).build();

        ModuleCodesContainsKeywordsPredicate predicate = new ModuleCodesContainsKeywordsPredicate(
                Arrays.asList(String.format("[%s]", VALID_MODULE_CODE_CS2106)));

        DeleteCommand deleteCommand = new DeleteCommand(predicate, new ModuleCode(VALID_MODULE_CODE_CS2106));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_NUMBER_DELETED_PERSON, 0)
                + String.format(DeleteCommand.MESSAGE_NUMBER_EDITED_PERSON, 1)
                + String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, person);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(person, newPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRangeFilteredList_throwsCommandException() {
        showPersonAtMultipleIndex(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Index invalidStartIndex = INDEX_SECOND_PERSON;
        Index invalidEndIndex = INDEX_THIRD_PERSON;
        //ensures that the invalidStartIndex and invalidEndIndex are still in bound of the address book list
        assertTrue(invalidStartIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        assertTrue(invalidEndIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(invalidStartIndex, invalidEndIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RANGE);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteBatchCommand1 = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        DeleteCommand deleteBatchCommand2 = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteBatchCommand1.equals(deleteBatchCommand1));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeleteCommand deleteBatchCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(deleteBatchCommand1.equals(deleteBatchCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        assertFalse(deleteBatchCommand1.equals(deleteBatchCommand2));
    }
}

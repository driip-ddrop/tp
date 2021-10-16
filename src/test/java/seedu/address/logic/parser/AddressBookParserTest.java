package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        List<String> aliasForClear = CommandWord.getAliasList(CommandWord.CLEAR);
        for (String alias : aliasForClear) {
            assertTrue(parser.parseCommand(alias) instanceof ClearCommand);
            assertTrue(parser.parseCommand(alias + " 3") instanceof ClearCommand);
        }

    }

    @Test
    public void parseCommand_delete() throws Exception {
        List<String> aliasForDelete = CommandWord.getAliasList(CommandWord.DELETE);
        for (String alias : aliasForDelete) {
            DeleteCommand command = (DeleteCommand) parser.parseCommand(
                    alias + " " + INDEX_FIRST_PERSON.getOneBased());
            assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
        }
    }

    @Test
    public void parseCommand_edit() throws Exception {
        List<String> aliasForEdit = CommandWord.getAliasList(CommandWord.EDIT);
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        for (String alias : aliasForEdit) {
            EditCommand command = (EditCommand) parser.parseCommand(alias + " "
                    + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
            assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
        }

    }

    @Test
    public void parseCommand_exit() throws Exception {
        List<String> aliasForExit = CommandWord.getAliasList(CommandWord.EXIT);
        for (String alias : aliasForExit) {
            assertTrue(parser.parseCommand(alias) instanceof ExitCommand);
            assertTrue(parser.parseCommand(alias + " 3") instanceof ExitCommand);
        }

    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                "find n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        List<String> aliasForHelp = CommandWord.getAliasList(CommandWord.HELP);
        for (String alias : aliasForHelp) {
            assertTrue(parser.parseCommand(alias) instanceof HelpCommand);
            assertTrue(parser.parseCommand(alias + " 3") instanceof HelpCommand);
        }

    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand("list") instanceof ListCommand);
        assertTrue(parser.parseCommand("list 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

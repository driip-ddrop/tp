package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleCodesContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_MODULE_CODE, PREFIX_TAG);
        boolean isNamePrefixPresent = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean isModulePrefixPresent = argMultimap.getValue(PREFIX_MODULE_CODE).isPresent();
        boolean isTagPrefixPresent = argMultimap.getValue(PREFIX_TAG).isPresent();

        long numberOfValidPrefixes = countValidPrefixes(isNamePrefixPresent, isModulePrefixPresent, isTagPrefixPresent);

        if (numberOfValidPrefixes == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (numberOfValidPrefixes > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_SINGLE_PREFIX_SEARCH)
            );
        }

        if (isNamePrefixPresent) {
            return getFindNameCommand(argMultimap);
        } else if (isModulePrefixPresent) {
            return getFindModuleCommand(argMultimap);
        } else {
            // if tag prefix is not present
            return getFindTagCommand(argMultimap);
        }
    }

    private long countValidPrefixes (Boolean... prefixPresent) {
        long count = Arrays.stream(prefixPresent).filter(x -> x).count();
        return count;
    }

    private FindCommand getFindNameCommand(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> searchInput = argMultimap.getValue(PREFIX_NAME);
        String names = searchInput.get().trim();
        if (names.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = names.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

    }

    private FindCommand getFindModuleCommand (ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> searchInput = argMultimap.getValue(PREFIX_MODULE_CODE);
        String modules = searchInput.get().trim();
        if (modules.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> moduleKeywordsList = Arrays.stream(modules.split("\\s+"))
                .map(module -> '[' + module + ']')
                .collect(Collectors.toList());

        return new FindCommand(new ModuleCodesContainsKeywordsPredicate(moduleKeywordsList));
    }

    private FindCommand getFindTagCommand (ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> searchInput = argMultimap.getValue(PREFIX_TAG);
        String tags = searchInput.get().trim();
        if (tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> tagKeywordsList = Arrays.stream(tags.split("\\s+"))
                .map(tag -> '[' + tag + ']')
                .collect(Collectors.toList());

        return new FindCommand(new TagsContainsKeywordsPredicate(tagKeywordsList));
    }
}

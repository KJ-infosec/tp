package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // --- Part A: General Search ---
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FACEBOOK,
                PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        boolean hasPrefix = argMultimap.containsPrefix(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_ADDRESS, PREFIX_FACEBOOK, PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        if (!hasPrefix) {
            return new FindCommand(new PersonContainsKeywordsPredicate(trimmedArgs));
        }

        // --- Part B: Specific Search (Prefixes) ---
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_ADDRESS, PREFIX_FACEBOOK, PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);

        List<Predicate<Person>> predicateList = new ArrayList<>();

        argMultimap.getValue(PREFIX_NAME).ifPresent(name ->
                predicateList.add(person -> person.getName().fullName.toLowerCase().contains(name.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(address ->
                predicateList.add(person -> person.getAddress().toString().toLowerCase().contains(address.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_PHONE).ifPresent(phone ->
                predicateList.add(person -> person.getPhone().toString().toLowerCase().contains(phone.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_TAG).ifPresent(tags ->
                predicateList.add(person -> person.getTags().stream()
                        .anyMatch(tag -> tag.toString().toLowerCase().contains(tags.toLowerCase()))
        ));

        argMultimap.getValue(PREFIX_FACEBOOK).ifPresent(fb ->
                predicateList.add(person -> person.getFacebook().toString().toLowerCase().contains(fb.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_INSTAGRAM).ifPresent(ig ->
                predicateList.add(person -> person.getInstagram().toString().toLowerCase().contains(ig.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_REMARK).ifPresent(remark ->
                predicateList.add(person -> person.getRemark().toString().toLowerCase().contains(remark.toLowerCase()))
        );

        if (predicateList.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(predicateList));
    }

    /**
     * Helper function to make sure the input value is non-empty.
     */
    private String getNonEmptyValue(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        String value = argMultimap.getValue(prefix).get().trim();
        if (value.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return value;
    }
}

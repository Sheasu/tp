package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Location;
import seedu.address.model.person.Products;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Returns an {@code EditCommand} parsed from the given {@code String} of arguments.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRODUCTS, PREFIX_LOCATION,
                PREFIX_DEADLINE, PREFIX_CONTACT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRODUCTS, PREFIX_LOCATION, PREFIX_DEADLINE,
                PREFIX_CONTACT);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PRODUCTS).isPresent()) {
            String rawProducts = argMultimap.getValue(PREFIX_PRODUCTS).get();
            editPersonDescriptor.setProducts(rawProducts.trim().isEmpty()
                    ? Products.empty()
                    : ParserUtil.parseProducts(rawProducts));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            String rawLocation = argMultimap.getValue(PREFIX_LOCATION).get();
            editPersonDescriptor.setLocation(rawLocation.trim().isEmpty()
                    ? Location.empty()
                    : ParserUtil.parseLocation(rawLocation));
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            String rawDeadline = argMultimap.getValue(PREFIX_DEADLINE).get();
            editPersonDescriptor.setDeadline(rawDeadline.trim().isEmpty()
                    ? Deadline.empty()
                    : ParserUtil.parseDeadline(rawDeadline));
        }
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            String rawContact = argMultimap.getValue(PREFIX_CONTACT).get();
            editPersonDescriptor.setContact(rawContact.trim().isEmpty()
                    ? Contact.empty()
                    : ParserUtil.parseContact(rawContact));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

}

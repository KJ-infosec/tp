package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain the given search phrase as a case-insensitive substring.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    /**
     * Specifies the type of search to perform.
     */
    public enum SearchType {
        NAME, ADDRESS, PHONE, FACEBOOK, TAG, INSTAGRAM, REMARK
    }
    private final String searchPhrase;
    private final boolean isGeneralSearch;
    private final List<Predicate<Person>> predicateList;

    /** Constructor for General Search */
    public PersonContainsKeywordsPredicate(String searchPhrase) {
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = true;
        this.predicateList = null;
    }

    /** Constructor for Specific Search */
    public PersonContainsKeywordsPredicate(List<Predicate<Person>> predicateList) {
        this.searchPhrase = null;
        this.isGeneralSearch = false;
        this.predicateList = predicateList;
    }

    @Override
    public boolean test(Person person) {
        if (isGeneralSearch) {
            return testGeneral(person);
        }
        return testSpecific(person);
    }


    private boolean testGeneral(Person person) {
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        return person.getName().fullName.toLowerCase().contains(lowerPhrase)
                || person.getPhone().map(p -> p.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getFacebook().map(fb -> fb.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getInstagram().map(ig -> ig.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getAddress().map(a -> a.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getRemark().map(r -> r.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerPhrase));
    }

    private boolean testSpecific(Person person) {
        return predicateList.stream().allMatch(p -> p.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return searchPhrase.equals(otherPredicate.searchPhrase);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchPhrase", searchPhrase).toString();
    }
}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Facebook("alex.yeoh"),
                new Instagram("alex.yeoh"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Remark("Prefers evening delivery"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Facebook("bernice.yu"),
                null, new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                null,
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), null,
                null, new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Remark("Allergic to seafood"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), null, null, new Instagram("DavidLi"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                null,
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Facebook("irfan.ibrahim"),
                null, new Address("Blk 47 Tampines Street 20, #17-35"),
                null,
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Facebook("roy.balakrishnan"),
                new Instagram("royyy"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Remark("Call before delivery"),
                getTagSet("colleagues"))
        };
    }

    public static Order[] getSampleOrders() {
        // Get sample persons and extract their IDs
        Person[] persons = getSamplePersons();
        UUID alexId = persons[0].getId();
        UUID berniceId = persons[1].getId();
        UUID charlotteId = persons[2].getId();
        UUID davidId = persons[3].getId();
        UUID irfanId = persons[4].getId();
        UUID royId = persons[5].getId();

        return new Order[] {
            // Alex Yeoh's orders
            new Order(alexId, new Item("Order 1: Pizza Margherita"), new Quantity("2"),
                new DeliveryTime("2026-05-10 1900"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Status("READY")),
            new Order(alexId, new Item("Order 2: Fried Rice"), new Quantity("1"),
                new DeliveryTime("2026-05-12 1200"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Status("PREPARING")),

            // Bernice Yu's orders
            new Order(berniceId, new Item("Order 3: Sushi Platter"), new Quantity("3"),
                new DeliveryTime("2026-05-08 1800"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Status("DELIVERED")),
            new Order(berniceId, new Item("Order 4: Grilled Salmon"), new Quantity("2"),
                new DeliveryTime("2026-05-15 1900"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Status("PREPARING")),

            // Charlotte Oliveiro's orders (avoiding seafood as per remark)
            new Order(charlotteId, new Item("Order 5: Beef Burger Meal"), new Quantity("1"),
                new DeliveryTime("2026-05-09 1730"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Status("DELIVERED")),

            // David Li's orders
            new Order(davidId, new Item("Order 6: Chicken Teriyaki"), new Quantity("2"),
                new DeliveryTime("2026-05-11 1200"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Status("READY")),

            // Irfan Ibrahim's orders
            new Order(irfanId, new Item("Order 7: Biryani"), new Quantity("3"),
                new DeliveryTime("2026-05-13 1900"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new Status("PREPARING")),
            new Order(irfanId, new Item("Order 8: Tandoori Chicken"), new Quantity("1"),
                new DeliveryTime("2026-05-05 1800"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new Status("CANCELLED")),

            // Roy Balakrishnan's orders
            new Order(royId, new Item("Order 9: Carbonara Pasta"), new Quantity("2"),
                new DeliveryTime("2026-05-14 1900"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Status("READY"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Order sampleOrder : getSampleOrders()) {
            sampleAb.addOrder(sampleOrder);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

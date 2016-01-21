package com.vennetics.bell.sam.model.subx;

/**
 * A POJO to represent an Attribute of a SubscriberProfile
 *
 * @author aaronwatters
 */
public class Attribute {

    private String name;
    private String value;

    public Attribute() {
        // For JSON deserialsation.
    }

    public Attribute(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}

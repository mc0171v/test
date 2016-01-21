package com.vennetics.bell.sam.model.subx;

import java.util.List;

/**
 * A POJO to represent the SubscriberProfile
 *
 * @author aaronwatters
 */
public class SubscriberProfile  {

    private List<Attribute> attributes;


    public SubscriberProfile() {
        // For JSON deserialsation.
    }

    public SubscriberProfile(final List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SubscriberProfile that = (SubscriberProfile) obj;

        return !(attributes != null ? !attributes.equals(that.attributes)
                        : that.attributes != null);

    }

    @Override
    public int hashCode()  {
        return attributes != null ? attributes.hashCode() : 0;
    }

    @Override public String toString() {
        String result = "SubscriberProfile{attributes=";
        for (Attribute attribute : attributes) {
            result += "{Name:" + attribute.getName() + " Value:" + attribute.getValue() + "}";
        }
        return result + '}';
    }
}

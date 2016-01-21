package com.vennetics.bell.sam.model.subx;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The test class for the SubscriberProfile POJO
 * 
 * @author aaronwatters
 */
public class SubscriberProfileTest {

    private List<Attribute> dummyAttributes;
    private List<Attribute> dummyAttributes2;

    @Before
    public void setUp() {
        // Create test data
        dummyAttributes = new ArrayList<>();
        dummyAttributes.add(new Attribute("min", "5142509795"));
        dummyAttributes.add(new Attribute("subId2", "XXXXXXXXXXXXXX-023X_bellmobility.ca"));
        dummyAttributes.add(new Attribute("eamLanguage", "E"));
        dummyAttributes.add(new Attribute("ou", "023"));
        dummyAttributes.add(new Attribute("meid", "000"));

        dummyAttributes2 = new ArrayList<>();
        dummyAttributes2.add(new Attribute("min", "5142509795"));
    }

    @Test
    public void testSubscriberProfilePojo() throws JsonProcessingException {

        // No arg constructor
        final SubscriberProfile noArgSubProfile = new SubscriberProfile();
        Assert.assertNull(noArgSubProfile.getAttributes());

        // All arg constructor
        final SubscriberProfile subscriberProfile = new SubscriberProfile(dummyAttributes);
        Assert.assertNotNull("The SubscriberProfile returned unexpected values", subscriberProfile.getAttributes());
        Assert.assertEquals("The SubscriberProfile returned unexpected values", dummyAttributes, subscriberProfile.getAttributes());
    }

    @Test
    public void testToString() {
        final SubscriberProfile subProfile = new SubscriberProfile(dummyAttributes);
        Assert.assertEquals(subProfile.toString(),
                            "SubscriberProfile{attributes={Name:min Value:5142509795}"
                                            + "{Name:subId2 Value:XXXXXXXXXXXXXX-023X_bellmobility.ca}"
                                            + "{Name:eamLanguage Value:E}"
                                            + "{Name:ou Value:023}"
                                            + "{Name:meid Value:000}}");
    }

    @Test
    public void testEqualsAndHashcode() {
        final SubscriberProfile subProfile1 = new SubscriberProfile(dummyAttributes);
        final SubscriberProfile subProfile2 = new SubscriberProfile(dummyAttributes);
        final SubscriberProfile subProfile3 = new SubscriberProfile(null);
        final SubscriberProfile subProfile4 = new SubscriberProfile(dummyAttributes2);

        // ***** Equals
        // Different Class
        Assert.assertFalse(subProfile1.equals(new Attribute("", "")));

        // Null Class
        Assert.assertFalse(subProfile1.equals(null));

        // Different attribute contents
        Assert.assertFalse(subProfile1.equals(subProfile3));
        Assert.assertFalse(subProfile1.equals(subProfile4));

        // Successful Comparison
        Assert.assertTrue(subProfile1.equals(subProfile1));
        Assert.assertTrue(subProfile1.equals(subProfile2));


        // ***** HashCode

        // Different Class
        Assert.assertFalse(subProfile1.hashCode() == new Attribute("", "").hashCode());

        // Different attribute contents
        Assert.assertFalse(subProfile1.hashCode() == subProfile3.hashCode());
        Assert.assertFalse(subProfile1.hashCode() == subProfile4.hashCode());

        // Successful Comparison
        Assert.assertTrue(subProfile1.hashCode() == subProfile1.hashCode());
        Assert.assertTrue(subProfile1.hashCode() == subProfile2.hashCode());
    }

}

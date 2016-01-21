package com.vennetics.bell.sam.model.subx;

import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class for the SubscriberProfile POJO
 *
 * @author aaronwatters
 */
public class AttributeTest extends TestCase {

    private String name;
    private String value;

    @Before
    public void setUp() {
        // Create test data
        name = "min";
        value = "5142509795";
    }

    @Test
    public void testAttributePojo() throws JsonProcessingException {

        final Attribute attribute = new Attribute(name, value);
        Assert.assertEquals("The Attribute returned an unexpected value",
                            name, attribute.getName());
        Assert.assertEquals("The Attribute returned an unexpected value",
                            value, attribute.getValue());
    }
}

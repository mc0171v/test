package com.vennetics.bell.sam.model.sdm.ldap;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The test class for the SdmAccount POJO
 *
 * @author aaronwatters
 */
public class SdmAccountTest extends TestCase {

    private Map<String, Set<String>> dummyAttributes;

    @Before
    public void setUp() {
        // Create test data
        Set<String> eamLanguageAttributes = new HashSet<>();
        eamLanguageAttributes.add("E");
        eamLanguageAttributes.add("F");
        eamLanguageAttributes.add("G");

        dummyAttributes = new HashMap<>();
        dummyAttributes.put("eamLanguage", eamLanguageAttributes);
    }

    @Test
    public void testSdmAccountPojo() throws Exception {

        final SdmAccount sdmAccount = new SdmAccount(dummyAttributes);
        Assert.assertNotNull("The SdmAccount returned unexpected values", sdmAccount);
        Assert.assertEquals("The SdmAccount returned unexpected values",
                            dummyAttributes, sdmAccount.getAttributes());
    }
}

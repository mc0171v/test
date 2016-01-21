package com.vennetics.bell.sam.subx.hystrix.commands;

import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The test class for GetSubscriberProfileCommand
 *
 * Created by aaronwatters on 04/01/2016.
 */
public class GetSubscriberProfileCommandTest extends TestCase {

    private Set<String> dummyAttributes;
    private Map<String, Set<String>> expectedResult;

    @Before
    public void setUp() throws Exception {
        dummyAttributes = new HashSet<>();
        dummyAttributes.add("MDN");
        dummyAttributes.add("banid");


        Set<String> eamLanguageAttributes = new HashSet<>();
        eamLanguageAttributes.add("E");
        eamLanguageAttributes.add("F");
        eamLanguageAttributes.add("G");

        Set<String> preCannedFilterValue = new HashSet<>();
        preCannedFilterValue.add("123456789");

        expectedResult = new HashMap<>();
        expectedResult.put("MDN", preCannedFilterValue);
        expectedResult.put("eamLanguage", eamLanguageAttributes);
        expectedResult.put("searchForAttributes", dummyAttributes);

    }

    @Test
    public void testRun() throws Exception {

        Observable<SdmAccount> getSubProfileCommand = new GetSubscriberProfileCommand(
                        "MDN", "123456789", dummyAttributes).observe();

        // blocking
        assertEquals("The GetSubscriberProfileCommand's run method returned unexpected values",
                     new SdmAccount(expectedResult).getAttributes(),
                     getSubProfileCommand.toBlocking().single().getAttributes());
    }
}

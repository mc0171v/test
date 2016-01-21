package com.vennetics.bell.sam.subx.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A HystrixCommand to perform the SDM Ldap I/O
 *
 * Created by aaronwatters on 21/12/2015.
 */
public class GetSubscriberProfileCommand extends HystrixCommand<SdmAccount> {

    private static final Logger LOG = LoggerFactory.getLogger(GetSubscriberProfileCommand.class);

    private final String searchFilter;
    private final String filterValue;
    private final Set<String> attributes;

    /**
     * GetSubscriberProfileCommand constructor
     *
     * @param searchFilter
     * @param filterValue
     * @param attributes
     */
    public GetSubscriberProfileCommand(final String searchFilter,
                                       final String filterValue,
                                       final Set<String> attributes) {
        super(HystrixCommandGroupKey.Factory.asKey("SubXSdmLdap"));
        this.searchFilter = searchFilter;
        this.filterValue = filterValue;
        this.attributes = attributes;
    }

    /**
     * This method will perform an SDM Ldap call
     *
     * @return SdmAccount
     */
    @Override protected SdmAccount run() {
        LOG.debug("Running Hystrix wrapped subscriberprofile command to return a populated SdmAccount");
        // Pre canned testing data for Sprint 2
        Set<String> eamLanguageAttributes = new HashSet<>();
        eamLanguageAttributes.add("E");
        eamLanguageAttributes.add("F");
        eamLanguageAttributes.add("G");

        Set<String> preCannedFilterValue = new HashSet<>();
        preCannedFilterValue.add(filterValue);

        final Map<String, Set<String>> result = new HashMap<>();
        result.put(searchFilter, preCannedFilterValue);
        result.put("eamLanguage", eamLanguageAttributes);
        result.put("searchForAttributes", attributes);

        return new SdmAccount(result);
    }
}

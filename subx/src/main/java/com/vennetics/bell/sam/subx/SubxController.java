package com.vennetics.bell.sam.subx;

import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import com.vennetics.bell.sam.model.subx.Attribute;
import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import com.vennetics.bell.sam.subx.ldap.dao.ISdmLdapDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Spring REST Controller for SubX
 *
 * @author aaronwatters
 */
@RestController
@RequestMapping("/api/subscriber")
public class SubxController {

    private static final Logger LOG = LoggerFactory.getLogger(SubxController.class);

    @Autowired
    private ISdmLdapDao subxLdapDao;

    /**
     * The GET endpoint for SubX
     *
     * @param searchFilter
     * @param filterValue
     * @return REST response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public SubscriberProfile getSubscriberProfile(
            @RequestParam(value = "searchFilter", required = true) final SearchFilterType searchFilter,
            @RequestParam(value = "filterValue", required = true) final String filterValue) {

        LOG.info("REST call for getSubscriberProfile with search filter: {} and filter "
                                 + "value: {}", searchFilter, filterValue);

        // This will eventually be hooked up to return the attributes allowed for each TPA
        final Set<String> attributes = new HashSet<>();
        attributes.add("MDN");
        attributes.add("banid");

        Observable<SdmAccount> sdmAccount = subxLdapDao.getSubscriberProfile(
                        searchFilter.toString(), filterValue, attributes);

        Observable<SubscriberProfile> observableProfile = sdmAccount.map(queryResult -> {
            Map<String, Set<String>> resultAttrs = queryResult.getAttributes();
            List<Attribute> subProAttributes = new ArrayList<>();

            for (Map.Entry<String, Set<String>> entrySet : resultAttrs.entrySet()) {
                Set<String> values = entrySet.getValue();
                // NOTE: assuming one value in result
                LOG.debug("Selecting first value from attributes return key: {}, Value: {}",
                          entrySet.getKey(), values.iterator().next());

                Attribute subProAttribute =
                                new Attribute(entrySet.getKey(), values.iterator().next());
                subProAttributes.add(subProAttribute);
            }
            return new SubscriberProfile(subProAttributes);
        });

        return observableProfile.toBlocking().single();
     }
}

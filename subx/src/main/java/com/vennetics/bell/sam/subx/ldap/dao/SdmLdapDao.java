package com.vennetics.bell.sam.subx.ldap.dao;

import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import com.vennetics.bell.sam.subx.hystrix.commands.GetSubscriberProfileCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Set;

/**
 * The LDAP DAO class
 *
 * @author aaronwatters
 */
@Service
public class SdmLdapDao implements ISdmLdapDao {

    private static final Logger LOG = LoggerFactory.getLogger(SdmLdapDao.class);

    /**
     * This method calls out to a HystrixCommand that returns SDM Ldap info for
     * getSubscriberProfile
     *
     * @param searchFilter
     * @param filterValue
     * @param attributes
     * @return SdmAccount
     */
    @Override
    public Observable<SdmAccount> getSubscriberProfile(
                    final String searchFilter, final String filterValue, final Set<String> attributes) {

        LOG.info("Sdm ldap call for getSubscriberProfile with search filter {} and filter "
                                 + "value {} with attributes {}", searchFilter, filterValue, attributes);

        return new GetSubscriberProfileCommand(searchFilter, filterValue, attributes).observe();
    }

}

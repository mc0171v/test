package com.vennetics.bell.sam.subx.ldap.dao;

import com.vennetics.bell.sam.model.sdm.ldap.SdmAccount;
import rx.Observable;

import java.util.Set;

/**
 * Interface for SDM Ldap calls
 */
public interface ISdmLdapDao {

    /**
     * @param filterAttribute
     * @param filterValue
     * @param attributes
     * @return SdmAccount
     */
    Observable<SdmAccount> getSubscriberProfile(final String filterAttribute, final String filterValue, final Set<String> attributes);

}

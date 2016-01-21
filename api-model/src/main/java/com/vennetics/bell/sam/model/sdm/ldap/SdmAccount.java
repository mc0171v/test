package com.vennetics.bell.sam.model.sdm.ldap;

import java.util.Map;
import java.util.Set;

/**
 * Class represents and SDM Account
 */
public class SdmAccount {
    private Map<String, Set<String>> attributes;

    /**
     * SdmAccount Constructor
     * @param attributes
     */
    public SdmAccount(final Map<String, Set<String>> attributes) {
        this.attributes = attributes;
    }
    
    /**
     * Getter to return a map that represents the attributes on the SDM LDAP
     *
     * @return attributes
     */
    public Map<String, Set<String>> getAttributes() {
        return  attributes;
    }
}

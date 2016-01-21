package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import java.util.List;
import rx.Observable;

/**
 * Defines an interface to the Subx REST service, for use by the ParlayX Soap adapter.
 */

public interface ISubxService {
    /**
     * Returns the profile for the requested subscriber address.
     * @param searchFilter
     *      One of (MDN,SUBID,MEID,UUID,IMSI)
     * @param filterValue
     *      The value of the filter
     * @return
     *      A {@link SubscriberProfile} Observable for the requested subscriber.
     */
    Observable<SubscriberProfile> getSubscriberProfile(SearchFilterType searchFilter, String filterValue);

    /**
     * Reports whether a set of feature codes are enabled.
     * @param mdn
     *      The mdn of the subscriber for which feature codes are to be checked
     * @param affiliateId
     *      The affiliate for which feature codes are to be checked.
     * @param featureCodes
     *      The feature codes to be checked.
     * @return
     *      A {@link List} of booleans, indicating whether the corresponding feature in <tt>featureCodes</tt>
     *      is enabled.
     */
    Observable<List<Boolean>> hasFeatureCodes(String mdn, String affiliateId, List<String> featureCodes);
}

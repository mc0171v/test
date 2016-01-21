package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.Attribute;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.SearchFilter;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0._interface.Subscriber;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.faults.PolicyException;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0.faults.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements ParlayX {@link Subscriber} interface, adapting SOAP to internal REST api.
 */

@WebService(endpointInterface = "generated.ca.bell.wsdl.thirdparty.subscriber.v1_0._interface.Subscriber")
@Component
public class SubscriberImpl implements Subscriber {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriberImpl.class);

    @Autowired(required = true)
    private ISubxService subxService;

    @Override
    public String getSubscriberPassword(final String subsMDN,
                                        final String affiliateID) throws PolicyException, ServiceException {
        return null;
    }

    @Override
    public List<Attribute> getSubscriberProfile(final SearchFilter searchFilter,
                                                final String filterValue) throws PolicyException, ServiceException {
        LOG.debug("getSubscriberProfile searchFilter:{} filterValue:{}", searchFilter, filterValue);

        final SubscriberProfile profile = getSubscriberProfileFromService(searchFilter, filterValue);
        LOG.debug("getSubscriberProfile profile:{}", profile);

        List<Attribute> result = mapProfileToAttributeList(profile);

        LOG.debug("getSubscriberProfile result:{}", result);
        return result;
    }

    @Override
    public boolean changeSubscriberPassword(final String subsMDN,
                                            final String oldPassword,
                                            final String newPassword,
                                            final String confirmNewPassword,
                                            final String affiliateID) throws PolicyException, ServiceException {
        return false;
    }

    @Override
    public List<Boolean> hasFeatureCodes(final String subsMdn,
                                         final String affiliateId,
                                         final List<String> featureCodes) throws PolicyException, ServiceException {
        final Observable<List<Boolean>> result = subxService.hasFeatureCodes(subsMdn, affiliateId, featureCodes);
        return result.toBlocking().single();
    }

    private List<Attribute> mapProfileToAttributeList(final SubscriberProfile profile) {
        return profile.getAttributes().stream()
                .map(SubscriberImpl::newAttribute)
                .collect(Collectors.toList());
    }

    private SubscriberProfile getSubscriberProfileFromService(final SearchFilter searchFilter,
                                                              final String filterValue) {

        return subxService.getSubscriberProfile(mapSearchFilterType(searchFilter), filterValue).toBlocking().single();
    }

    // Map external search filter type (defined by WSDL) to internal.
    private static SearchFilterType mapSearchFilterType(final SearchFilter searchFilter) {
        return SearchFilterType.valueOf(searchFilter.toString());
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod"}) // It is used! But sonarqube doesn't recognise it.
    private static Attribute newAttribute(final com.vennetics.bell.sam.model.subx.Attribute entry) {
        final Attribute attribute = new Attribute();
        attribute.setKey(entry.getName());
        attribute.setValue(entry.getValue());
        return attribute;
    }
}

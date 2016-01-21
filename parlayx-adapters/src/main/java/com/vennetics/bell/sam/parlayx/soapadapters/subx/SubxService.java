package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import com.vennetics.bell.sam.model.subx.SearchFilterType;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import com.vennetics.bell.sam.parlayx.soapadapters.subx.commands.GetSubscriberProfileCommand;
import com.vennetics.bell.sam.parlayx.soapadapters.subx.commands.HasFeatureCodesCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;

/**
 * Default implementation of {@link ISubxService}.
 */

@Service
@EnableHystrix
public class SubxService implements ISubxService {

    private static final Logger LOG = LoggerFactory.getLogger(SubxService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Observable<SubscriberProfile> getSubscriberProfile(final SearchFilterType searchFilter, final String filterValue) {
        LOG.debug("getSubscriberProfile searchFilter:{} filterValue:{}", searchFilter, filterValue);
        return new GetSubscriberProfileCommand(searchFilter.toString(), filterValue, restTemplate).observe();
    }

    @Override
    public Observable<List<Boolean>> hasFeatureCodes(final String mdn, final String affiliateId, final List<String> featureCodes) {
        LOG.debug("hasFeatureCodes mdn:{} affiliateId:{} featureCodes:{}", mdn, affiliateId, featureCodes);
        return new HasFeatureCodesCommand(mdn, affiliateId, featureCodes, restTemplate).observe();
    }
}

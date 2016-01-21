package com.vennetics.bell.sam.parlayx.soapadapters.subx.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.vennetics.bell.sam.model.subx.SubscriberProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * A HystrixCommand to invoke GetSubscriberProfile on the internal SUBX service
 */

public class GetSubscriberProfileCommand extends HystrixCommand<SubscriberProfile> {

    private static final Logger LOG = LoggerFactory.getLogger(GetSubscriberProfileCommand.class);

    private final String searchFilter;
    private final String filterValue;
    private final RestTemplate template;

    /**
     * Sole constructor.
     * @param searchFilter
     *      One of (MDN,SUBID,MEID,UUID,IMSI)
     * @param filterValue
     *      The value of the filter
     * @param template
     *      The {@link RestTemplate} to be used to address the service.
     */
    public GetSubscriberProfileCommand(final String searchFilter,
                                       final String filterValue,
                                       final RestTemplate template) {
        super(HystrixCommandGroupKey.Factory.asKey("SubxGetSubscriberProfile"));
        this.searchFilter = searchFilter;
        this.filterValue = filterValue;
        this.template = template;
    }

    @Override
    protected SubscriberProfile run() {
        LOG.debug("GetSubscriberProfileCommand searchFilter:{} filterValue:{}", searchFilter, filterValue);

        final SubscriberProfile result = template.getForObject(buildUrl(), SubscriberProfile.class);

        LOG.debug("getSubscriberProfile result {}", result);
        return result;
    }

    private String buildUrl() {
        return String.format("http://subx/api/subscriber/profile?searchFilter=%s&filterValue=%s", searchFilter, filterValue);
    }
}

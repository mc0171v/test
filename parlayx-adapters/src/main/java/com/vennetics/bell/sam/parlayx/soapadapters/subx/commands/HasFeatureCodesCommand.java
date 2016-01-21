package com.vennetics.bell.sam.parlayx.soapadapters.subx.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * A HystrixCommand to invoke HasFeatureCodes on the internal SUBX service
 */

public class HasFeatureCodesCommand extends HystrixCommand<List<Boolean>> {

    private static final Logger LOG = LoggerFactory.getLogger(HasFeatureCodesCommand.class);

    private final String mdn;
    private final String affiliateId;
    private final List<String> featureCodes;
    private final RestTemplate template;

    /**
     * Sole constructor.
     * @param mdn
     *      The mdn for which feature code enablement is to be checked.
     * @param affiliateId
     *      The affiliate for which feature code enablement is to be checked.
     * @param featureCodes
     *      The feature codes to be checked.
     * @param template
     *      The {@link RestTemplate} to be used to address the service.
     */
    public HasFeatureCodesCommand(final String mdn,
                                  final String affiliateId,
                                  final List<String> featureCodes,
                                  final RestTemplate template) {
        super(HystrixCommandGroupKey.Factory.asKey("SubxHasFeatureCodes"));
        this.mdn = mdn;
        this.affiliateId = affiliateId;
        this.featureCodes = featureCodes;
        this.template = template;
    }

    @Override
    protected List<Boolean> run() {
        LOG.debug("HasFeatureCodesCommand mdn:{} affiliateId:{} featureCodes:{}", mdn, affiliateId, featureCodes);

        final List<Boolean> result = template.getForObject(buildUrl(), BooleanList.class);

        LOG.debug("HasFeatureCodesCommand result {}", result);
        return result;
    }

    private String buildUrl() {
        // TODO SJ Address this after agreeing interface to internal service.
        return String.format("http://subx/api/subscriber/featureCodes?mdn=%s&affiliateId=%s", mdn, affiliateId);
    }

    /**
     * To simplify response type definition in RestTemplate invocation.
     */
    protected static class BooleanList extends ArrayList<Boolean> {

    }
}

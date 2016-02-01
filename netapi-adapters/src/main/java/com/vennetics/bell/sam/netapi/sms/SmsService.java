package com.vennetics.bell.sam.netapi.sms;

import com.vennetics.bell.sam.netapi.config.RestTemplateFactory;
import com.vennetics.bell.sam.netapi.sms.commands.SendMessageCommand;
import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of {@link ISmsService}.
 */

@Service
@EnableHystrix
public class SmsService implements ISmsService, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateFactory templateFactory;

    @Override
    public ResponseEntity<OutboundSMSMessageRequest> sendMessage(final String senderAddress,
                                                                 final OutboundSMSMessageRequest message) {
        LOG.debug("sendMessage senderAddress:{} message:{}", senderAddress, message);
        return new SendMessageCommand(senderAddress, message, restTemplate).observe().toBlocking().single();
    }

    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "squid:S00112"})
    public void afterPropertiesSet() throws Exception {
        restTemplate = templateFactory.netapiRestTemplate();
    }
}


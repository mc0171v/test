package com.vennetics.bell.sam.netapi.sms.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * A HystrixCommand to invoke SendMessage on the internal SMS service
 */

public class SendMessageCommand extends HystrixCommand<ResponseEntity<OutboundSMSMessageRequest>> {

    private static final Logger LOG = LoggerFactory.getLogger(SendMessageCommand.class);

    private final String senderAddress;
    private final OutboundSMSMessageRequest request;
    private final RestTemplate template;

    /**
     * Sole constructor.
     * @param senderAddress
     *      The address of the sender.
     * @param request
     *      Defines the message to be sent.
     * @param template
     *      The {@link RestTemplate} to be used to address the service.
     */
    public SendMessageCommand(final String senderAddress,
                              final OutboundSMSMessageRequest request,
                              final RestTemplate template) {
        super(HystrixCommandGroupKey.Factory.asKey("SmsSendMessage"));
        this.senderAddress = senderAddress;
        this.request = request;
        this.template = template;
    }

    @Override
    protected ResponseEntity<OutboundSMSMessageRequest> run() {
        LOG.debug("SendMessageCommand senderAddress:{} request:{}", senderAddress, request);

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final HttpEntity<OutboundSMSMessageRequest> entity = new HttpEntity<>(request, headers);
        final ResponseEntity<OutboundSMSMessageRequest> response =
                                        template.postForEntity(buildUrl(), entity, OutboundSMSMessageRequest.class);

        LOG.debug("SendMessageCommand result {}", response);
        return response;
    }

    private String buildUrl() {
        // TODO SJ Define URLs properly and centrally...
        return String.format("http://netapi-sms-enabler/outbound/%s/requests", senderAddress);
    }
}

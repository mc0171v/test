package com.vennetics.bell.sam.netapi.sms;

import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST Controller for SMS.
 * This implementation simply forwards to the internal SMS service.
 */

@RestController
@RequestMapping(SmsController.REST_NETAPI_SMS_1_URL)
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);

    protected static final String REST_NETAPI_SMS_1_URL = "/oma/xml/rest/netapi/sms/1";

    @Autowired
    private ISmsService smsService;

    /**
     * The POST endpoint for SMS...
     * @return REST response
     */

    /**
     * POST endpoint used to create an outgoing message request.
     * @param senderAddress
     *      Address of the sender
     * @param message
     *      Defines the message to be sent
     * @return
     *      an {@link OutboundSMSMessageRequest} including delivery information.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/outbound/{senderAddress}/requests")
    public ResponseEntity<OutboundSMSMessageRequest> sendSmsMessage(@PathVariable final String senderAddress,
                                                                    @RequestBody final OutboundSMSMessageRequest message) {

        LOG.debug("sendSmsMessage senderAddress:{}", senderAddress);

        final ResponseEntity<OutboundSMSMessageRequest> response = forwardRequest(senderAddress, message);

        LOG.debug("sendSmsMessage response:{}", response);
        return response;
    }

    private ResponseEntity<OutboundSMSMessageRequest> forwardRequest(final String senderAddress,
                                                                     final OutboundSMSMessageRequest message) {
        return smsService.sendMessage(senderAddress, message);
    }
}

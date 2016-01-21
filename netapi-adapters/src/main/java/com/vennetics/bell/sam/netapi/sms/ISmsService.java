package com.vennetics.bell.sam.netapi.sms;

import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;
import org.springframework.http.ResponseEntity;

/**
 * Defines an interface to the internal SMS REST service, for use by the Netapi adapter.
 */

@SuppressWarnings("squid:S1609") // Avoid Sonarqube suggestion about FunctionalInterface. Other methods will follow...
public interface ISmsService {
    /**
     * Submits a message request.
     * @param senderAddress
     *      The address of the sender
     * @param message
     *      The message to be sent.
     * @return
     *      An {@link OutboundSMSMessageRequest} ResponseEntity defining the delivery status.
     */
    ResponseEntity<OutboundSMSMessageRequest> sendMessage(String senderAddress, OutboundSMSMessageRequest message);

}

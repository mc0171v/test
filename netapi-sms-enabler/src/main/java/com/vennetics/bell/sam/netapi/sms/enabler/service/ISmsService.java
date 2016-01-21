package com.vennetics.bell.sam.netapi.sms.enabler.service;

import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;

public interface ISmsService {

    /**
     * Submits a binary message request.
     * 
     * @param senderAddress
     *            The address of the sender
     * @param binaryMessage
     *            The binary message to be sent.
     * @return An {@link OutboundSMSMessageRequest} defining the
     *         delivery status.
     */
    OutboundSMSMessageRequest sendBinaryMessage(String senderAddress,
                                                OutboundSMSMessageRequest binaryMessage);

}

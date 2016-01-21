package com.vennetics.bell.sam.netapi.sms.enabler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import generated.oma.xml.rest.netapi.sms._1.DeliveryInfo;
import generated.oma.xml.rest.netapi.sms._1.DeliveryInfoList;
import generated.oma.xml.rest.netapi.sms._1.DeliveryStatus;
import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;

@Service
public class SmsBinaryService implements ISmsService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsBinaryService.class);

    @Override
    public OutboundSMSMessageRequest sendBinaryMessage(final String senderAddress,
                                                       final OutboundSMSMessageRequest binaryMessage) {
        LOG.debug(">>> sendBinaryMessage({}, {})" + binaryMessage, binaryMessage.getAddresses());
        final DeliveryInfoList deliveryInfoList = new DeliveryInfoList();
        for (final String deliveryAddress : binaryMessage.getAddresses()) {
            final DeliveryInfo deliveryInfo = new DeliveryInfo();
            deliveryInfo.setAddress(deliveryAddress);
            deliveryInfo.setDeliveryStatus(DeliveryStatus.MESSAGE_WAITING);
            deliveryInfoList.getDeliveryInfos().add(deliveryInfo);
        }
        binaryMessage.setDeliveryInfoList(deliveryInfoList);
        return binaryMessage;
    }

}

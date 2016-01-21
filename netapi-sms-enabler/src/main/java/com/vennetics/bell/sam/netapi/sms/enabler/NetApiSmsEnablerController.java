package com.vennetics.bell.sam.netapi.sms.enabler;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.vennetics.bell.sam.netapi.sms.enabler.service.ISmsService;

import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;

/**
 * 
 * @author markcorkery
 *
 */
@RestController
@RequestMapping("/")
public class NetApiSmsEnablerController {

    @Value("${enabler.uri.basePath}")
    private String basePath;

    @Autowired
    private ISmsService smsService;

    private static final Logger LOG = LoggerFactory.getLogger(NetApiSmsEnablerController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/outbound/{senderAddress}/requests")
    public ResponseEntity<OutboundSMSMessageRequest> sendSmsMessage(@PathVariable final String senderAddress,
                                                                    @RequestBody final OutboundSMSMessageRequest message,
                                                                    final UriComponentsBuilder ucBuilder) {
        LOG.debug(">>> sendSmsMessage({}, {})" + senderAddress, message);
        final OutboundSMSMessageRequest outboundResponse = smsService.sendBinaryMessage(senderAddress,
                                                                                        message);
        final URI resourceUri = ucBuilder.path("{base}/outbound/{senderAddress}/requests/{requestId}")
                                         .buildAndExpand(basePath,
                                                         senderAddress,
                                                         UUID.randomUUID().toString())
                                         .toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(resourceUri);
        outboundResponse.setResourceURL(resourceUri.toString());
        return new ResponseEntity<OutboundSMSMessageRequest>(outboundResponse,
                                                             headers,
                                                             HttpStatus.CREATED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    void handleBadRequests(final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                           "Please try again and with a non empty string as 'name'");
    }
}

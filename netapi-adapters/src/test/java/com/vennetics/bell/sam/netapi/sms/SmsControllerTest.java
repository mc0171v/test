package com.vennetics.bell.sam.netapi.sms;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import com.vennetics.bell.sam.netapi.TestConfiguration;
import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The Test class for SubxController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@WebIntegrationTest({ "server.port=0", "management.port=0", "spring.cloud.config.enabled=false"})
public class SmsControllerTest {

    private static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final MediaType XML_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(), Charset.forName("utf8"));

    public static final String OUTBOUND_REQUEST_URL = SmsController.REST_NETAPI_SMS_1_URL + "/outbound/tel1234/requests";

    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter jsonMessageConverter;

    @Autowired
    private MappingJackson2XmlHttpMessageConverter xmlMessageConverter;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private SmsController controller;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(jsonMessageConverter, xmlMessageConverter)
                .build();
    }

    @Test
    public void shouldAcceptJson() throws Exception {

        final ResponseEntity<OutboundSMSMessageRequest> response = dummyResponse();

        when(smsService.sendMessage(eq("tel1234"), isA(OutboundSMSMessageRequest.class))).thenReturn(response);

        mockMvc.perform(post(OUTBOUND_REQUEST_URL)
                .accept(JSON_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody())).andDo(print())
               .andExpect(status().isCreated())
               .andExpect(content().contentType(JSON_MEDIA_TYPE))
               .andExpect(jsonPath("$.outboundSMSMessageRequest.senderName").value("ABC"))
               .andExpect(jsonPath("$.outboundSMSMessageRequest.address").isArray());
    }

    @Test
    public void shouldAcceptXml() throws Exception {

        final ResponseEntity<OutboundSMSMessageRequest> response = dummyResponse();

        when(smsService.sendMessage(eq("tel1234"), isA(OutboundSMSMessageRequest.class))).thenReturn(response);

        mockMvc.perform(post(OUTBOUND_REQUEST_URL)
                .accept(XML_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_XML)
                .content(xmlBody())).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(XML_MEDIA_TYPE))
                .andExpect(xpath("//outboundSMSMessageRequest").exists())
                .andExpect(xpath("//outboundSMSMessageRequest/senderName").string("ABC"))
                .andExpect(xpath("//outboundSMSMessageRequest/address").nodeCount(2));
    }


    private String jsonBody() {
        return sourceWithLines(
                "{\"outboundSMSMessageRequest\": {",
                "  \"address\": [",
                "    \"tel:+19585550101\",",
                "    \"tel:+19585550104\"",
                "  ],",
                "  \"clientCorrelator\": \"67893\",",
                "  \"outboundSMSTextMessage\": {\"message\": \"Example Text Message\"},",
                "  \"receiptRequest\": {\"notifyURL\": \"http://application.example.com/notifications/DeliveryInfoNotification\"},",
                "  \"senderAddress\": \"tel:+19585550151\",",
                "  \"senderName\": \"MyNameJSON\"",
                "}}"
        );
    }

    private String xmlBody() {
        return sourceWithLines(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<sms:outboundSMSMessageRequest xmlns:sms=\"urn:oma:xml:rest:netapi:sms:1\">",
                "    <address>tel:+19585550101</address>",
                "    <address>tel:+19585550104</address>",
                "    <senderAddress>tel:+19585550151</senderAddress>",
                "    <senderName>MyNameXML</senderName>",
                "    <receiptRequest>",
                "        <notifyURL>http://application.example.com/notifications/DeliveryInfoNotification</notifyURL>",
                "    </receiptRequest>",
                "    <outboundSMSTextMessage>",
                "        <message>Example Text Message </message>",
                "    </outboundSMSTextMessage>",
                "    <clientCorrelator>67893</clientCorrelator>",
                "</sms:outboundSMSMessageRequest>"
        );
    }

    private String sourceWithLines(final String ... lines) {
        return Arrays.stream(lines).collect(Collectors.joining("\n"));
    }

    private ResponseEntity<OutboundSMSMessageRequest> dummyResponse() {
        final OutboundSMSMessageRequest response = new OutboundSMSMessageRequest();
        response.setSenderName("ABC");
        response.getAddresses().add("Addr1");
        response.getAddresses().add("Addr2");
        return new ResponseEntity<OutboundSMSMessageRequest>(response, HttpStatus.CREATED);
    }


}



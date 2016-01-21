package com.vennetics.bell.sam.netapi.sms.enabler;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.vennetics.bell.sam.netapi.sms.enabler.service.ISmsService;
import com.vennetics.shared.test.utils.categories.IntegrationTest;

import generated.oma.xml.rest.netapi.sms._1.OutboundSMSMessageRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetApiSmsEnablerApplication.class)
@WebIntegrationTest({ "server.port=0", "management.port=0", "spring.cloud.config.enabled=false",
        "eureka.client.registerWithEureka:false", "eureka.client.fetchRegistry:false" })
@Category(IntegrationTest.class)
public class NetApiSmsEnablerControllerTest {

    @Autowired
    private NetApiSmsEnablerController controllerUnderTest;

    @Autowired
    private MappingJackson2HttpMessageConverter jsonMessageConverter;

    @Autowired
    private MappingJackson2XmlHttpMessageConverter xmlMessageConverter;

    private MockMvc mockMvc;

    private ISmsService smsService;

    private String jsonBody = "{\"outboundSMSMessageRequest\": {"
                    + "\"address\": [\"tel:+19585550101\",\"tel:+19585550104\"],"
                    + "\"clientCorrelator\": \"67893\","
                    + "\"outboundSMSTextMessage\": {\"message\": \"Example Text Message\"},"
                    + "\"receiptRequest\": {\"notifyURL\": \"http://application.example.com/notifications/DeliveryInfoNotification\"},"
                    + "\"senderAddress\": \"tel:+19585550151\"," + "\"senderName\": \"MyName\""
                    + "}}";

    private static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                                   MediaType.APPLICATION_JSON.getSubtype(),
                                                                   Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        smsService = mock(ISmsService.class);
        ReflectionTestUtils.setField(controllerUnderTest, "smsService", smsService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest)
                                      .setMessageConverters(jsonMessageConverter,
                                                            xmlMessageConverter)
                                      .build();
    }

    @Test
    public void shouldInvokeSmsServiceService() throws Exception {
        final OutboundSMSMessageRequest response = new OutboundSMSMessageRequest();
        response.setSenderName("ABC");
        response.getAddresses().add("Addr1");
        response.getAddresses().add("Addr2");
        when(smsService.sendBinaryMessage(eq("tel1234"),
                                          isA(OutboundSMSMessageRequest.class))).thenReturn(response);
        mockMvc.perform(post("/outbound/tel1234/requests").accept(JSON_MEDIA_TYPE)
                                                          .contentType(MediaType.APPLICATION_JSON)
                                                          .content(jsonBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(content().contentType(JSON_MEDIA_TYPE))
               .andExpect(jsonPath("$.outboundSMSMessageRequest").exists())
               .andExpect(jsonPath("$.outboundSMSMessageRequest.senderName").value("ABC"))
               .andExpect(jsonPath("$.outboundSMSMessageRequest.address").isArray())
               .andExpect(jsonPath("$.outboundSMSMessageRequest.resourceURL").exists())
               .andExpect(header().string("Location", containsString("/outbound/tel1234/request")));
    }

}

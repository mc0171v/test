package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0._interface.Subscriber;
import org.apache.cxf.jaxws.EndpointImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class SubxConfigurationTest {

    @Autowired
    @InjectMocks
    private SubxConfiguration configuration;

    @Mock
    private Subscriber mockSubscriber;

    @Test
    public void shouldDeliverEndpoint() throws Exception {

        final EndpointImpl endpoint = configuration.subscriberEndpoint();

        assertThat(endpoint, is(not(nullValue())));
        assertThat(endpoint.getAddress(), is("/subscriber/v1_0"));
    }
}


package com.vennetics.bell.sam.parlayx.soapadapters.subx;

import generated.ca.bell.wsdl.thirdparty.subscriber.v1_0._interface.Subscriber;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures a {@link Subscriber} endpoint.
 */

@Configuration
public class SubxConfiguration {

    @Autowired(required = true)
    private Subscriber subscriber;

    @Bean
    public EndpointImpl subscriberEndpoint() {
        final EndpointImpl endpoint = new EndpointImpl(subscriber);
        endpoint.publish("/subscriber/v1_0");
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        return endpoint;
    }
}

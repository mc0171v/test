package com.vennetics.bell.sam.parlayx.soapadapters;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Configuration for a CXF Servlet.
 */

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfConfiguration {

    protected static final String SOAP_URL_PREFIX = "/ca/bell/wsdl/thirdparty/*";

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new CXFServlet(), SOAP_URL_PREFIX);
    }
}

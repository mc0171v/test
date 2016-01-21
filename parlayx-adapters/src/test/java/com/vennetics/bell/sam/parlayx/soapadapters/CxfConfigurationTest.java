package com.vennetics.bell.sam.parlayx.soapadapters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.ServletRegistrationBean;

public class CxfConfigurationTest {

    private CxfConfiguration configuration;

    @Before
    public void before() throws Exception {
        configuration = new CxfConfiguration();
    }

    @Test
    public void shouldGetServletRegistrationBean() throws Exception {
        final ServletRegistrationBean bean = configuration.servletRegistrationBean();
        assertThat(bean, is(not(nullValue())));
        assertThat(bean.getUrlMappings().size(), is(1));
        assertThat(bean.getUrlMappings().iterator().next(), is(CxfConfiguration.SOAP_URL_PREFIX));
    }
}

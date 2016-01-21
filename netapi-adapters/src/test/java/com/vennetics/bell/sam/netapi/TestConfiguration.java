package com.vennetics.bell.sam.netapi;

import static org.mockito.Mockito.mock;
import com.vennetics.bell.sam.netapi.sms.ISmsService;
import com.vennetics.bell.sam.netapi.config.RestTemplateFactory;
import com.vennetics.bell.sam.netapi.sms.SmsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Dummy mainline to define configuration for web integration testing.
 * Replace SmsService bean with a mock. TODO SJ Determine if there is a better way of doing this...
 */

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.vennetics.bell.sam.netapi",
               excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SmsService.class),
                                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RestTemplateFactory.class)
               })
public class TestConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(TestConfiguration.class, args);
    }

    @Bean
    public ISmsService smsService() {
        return mock(ISmsService.class);
    }
}

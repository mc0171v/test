package com.vennetics.bell.sam.netapi.sms.enabler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Application for HATA SmsX
 *
 * @author markcorkery
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = { "com.vennetics.bell.sam" })
@SuppressWarnings({ "checkstyle:hideutilityclassconstructor", "squid:S1118" })
public class NetApiSmsEnablerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NetApiSmsEnablerApplication.class, args);
    }

}

package com.vennetics.bell.sam.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@EnableAdminServer
@SuppressWarnings({"checkstyle:hideutilityclassconstructor", "squid:S1118"})
public class SpringBootAdminApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }
}

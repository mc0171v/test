package com.vennetics.bell.sam.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@SuppressWarnings({"checkstyle:hideutilityclassconstructor", "squid:S1118"})
public class HelloWorldApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}

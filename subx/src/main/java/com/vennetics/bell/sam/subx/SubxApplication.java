package com.vennetics.bell.sam.subx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Spring Application for SubX
 *
 * @author aaronwatters
 */
@SpringBootApplication
@EnableEurekaClient
@SuppressWarnings({"checkstyle:hideutilityclassconstructor", "squid:S1118"})
public class SubxApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SubxApplication.class, args);
    }

}

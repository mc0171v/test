package com.vennetics.bell.sam.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
@SuppressWarnings({"checkstyle:hideutilityclassconstructor", "squid:S1118"})
public class TurbineApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }
}

package com.vennetics.bell.sam.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@EnableAutoConfiguration
public class HelloWorldController {

    @Value("${bar:World!}")
    private String bar;

    @RequestMapping("/helloworld")
    public String message() {
        return bar;
    }

}

package com.vennetics.bell.sam.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "squid:S00112"})
    public void configureAuth(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("simon")
                .password("james")
                .roles("USER")
                .and()
                .withUser("mark")
                .password("corkery")
                .roles("USER")
                .and()
                .withUser("aaron")
                .password("watters")
                .roles("USER", "ADMIN");
    }

    @Override
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "squid:S00112"})
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**")
                .hasRole("USER")
                .antMatchers("/ca/bell/wsdl/thirdparty/**") // ParlayX
                .hasRole("USER")
                .antMatchers("/oma/xml/rest/netapi/**") // Netapi
                .hasRole("USER")
                .antMatchers("/hystrix.stream")
                .permitAll()
                .antMatchers("/health")
                .permitAll()
                .antMatchers("/**")
                .hasAnyRole("ADMIN")
                .and()
                .httpBasic();
    }
}

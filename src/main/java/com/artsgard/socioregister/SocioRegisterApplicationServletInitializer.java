package com.artsgard.socioregister;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author artsgard
 * To generate a war to deploy you need this class and you need to change in the pom the jar into war
 * After the build you will find the war at the target folder
 */
public class SocioRegisterApplicationServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(SocioRegisterApplication.class);
    }
}

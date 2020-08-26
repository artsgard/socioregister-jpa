package com.artsgard.socioregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource({"classpath:application.properties", "classpath:postgres.properties"})
@ComponentScan("com.artsgard")

public class SocioRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocioRegisterApplication.class, args);
    }
}

package com.mediscreen.microservicepatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class MicroservicePatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicePatientApplication.class, args);
    }

}

package com.mediscreen.mediscreenwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen")
@EnableDiscoveryClient
@EnableConfigurationProperties
public class MediscreenWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenWebappApplication.class, args);
	}

}

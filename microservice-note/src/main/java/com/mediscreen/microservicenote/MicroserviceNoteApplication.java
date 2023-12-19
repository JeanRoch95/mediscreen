package com.mediscreen.microservicenote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceNoteApplication.class, args);
	}

}

package io.github.palharesricardo.clientejavaapi;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * Class that starts the application
 * 
 */
@Log4j2
@SpringBootApplication
public class clientejavaapiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(clientejavaapiApplication.class, args);
		log.info("clientejavaapi started successfully at {}", LocalDateTime.now());
	}

}

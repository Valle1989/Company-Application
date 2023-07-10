package com.fvalle.company;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CompanyApplication implements CommandLineRunner {

	//private final Log LOGGER = LogFactory.getLog(CompanyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//LOGGER.debug("Error en el  navegador");
	}
}

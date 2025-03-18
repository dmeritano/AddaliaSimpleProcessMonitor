package com.addalia.simplemonitor;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AddaliaSimpleProcessMonitorBackendApplication implements CommandLineRunner{

	private static Logger logger = LoggerFactory.getLogger(AddaliaSimpleProcessMonitorBackendApplication.class);
		
	
	public static void main(String[] args) {
		SpringApplication.run(AddaliaSimpleProcessMonitorBackendApplication.class, args);
		logger.info("AddaliaSimpleProcessMonitorBackend is ready!");
	}

	@Override
	public void run(String... args) throws Exception {		

		
		//System.out.println("'admin' pass bcrypt es: " + new BCryptPasswordEncoder().encode("admin"));
		//System.out.println("'addalia' pass bcrypt es: " + new BCryptPasswordEncoder().encode("addalia2025"));
		
		
	}	
}

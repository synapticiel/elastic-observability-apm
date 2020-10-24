package com.packt.cardatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootApplication
public class CardatabaseApplication /* extends SpringBootServletInitializer */ {

	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

	// Inject repositories
	@Autowired
	private CarRepository repository;
	@Autowired
	private OwnerRepository orepository;

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Hello Spring Boot, This is an example of info log");
		logger.warn("Hello Spring Boot, This is an example of warn log");
		logger.error("Hello Spring Boot, This is an example of error log");
		logger.debug("Hello Spring Boot, This is an example of debug log");
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// Add owner objects and save these to db
			Owner owner = new Owner("Yassine", "Lasri");
			orepository.save(owner);
			// Add car object with link to owners and save these to db.
			Car car = new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 59000, 58321, owner);
			repository.save(car);
			car = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2014, 29000, 31998, owner);
			repository.save(car);
			car = new Car("Toyota", "Prius", "Silver", "KKO-0212", 2018, 39000, 41556, owner);
			repository.save(car);
		};
	}
}

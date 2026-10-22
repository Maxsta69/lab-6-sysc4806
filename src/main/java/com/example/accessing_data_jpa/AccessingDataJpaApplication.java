package com.example.accessing_data_jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {

	private static final Logger logger = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class, args);
	}

	//@Bean
	public CommandLineRunner demo(AddressBookRepository ABRepo, BuddyInfoRepository BIRepo) {
		return args -> {
			AddressBook book = new AddressBook();
			book.addBuddy(new BuddyInfo("Max", "23 Bank Street", "613-501-4444", "maxbui@example.ca"));
			book.addBuddy(new BuddyInfo("Aidan", "24 Bank Street", "343-444-5555", "aidan@example.ca"));
			AddressBook book2 = new AddressBook();
			book2.addBuddy(new BuddyInfo("Max", "300 Booth Street", "613-680-5230", "maxbui@cmail.carleton.ca"));
			book2.addBuddy(new BuddyInfo("Karina", "1000 Colonel By Road", "613-111-2222", "karina@example.ca"));
			book2.addBuddy(new BuddyInfo("Aditya", "67 Prince of Wales", "613-666-7777", "aditya.@example.ca"));
			ABRepo.save(book);
			ABRepo.save(book2);

			logger.info("Address books found with findAll():");
			logger.info("-----------------------------------");
			ABRepo.findAll().forEach(ab -> logger.info(ab.toString()));
			logger.info("");

			logger.info("Buddies found with findAll():");
			logger.info("-----------------------------------");
			BIRepo.findAll().forEach(b -> logger.info(b.toString()));
			logger.info("");

			logger.info("Buddies found with findByName(\"Max\"):");
			logger.info("-----------------------------------");
			BIRepo.findByName("Max").forEach(b -> logger.info(b.toString()));
			logger.info("");

			logger.info("Buddies found with findByName(\"Aidan\"):");
			logger.info("-----------------------------------");
			BIRepo.findByName("Aidan").forEach(b -> logger.info(b.toString()));
			logger.info("");
		};
	}
}

package com.jagt.bootstrap;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.jagt.*.infrastructure",
		"com.jagt.*.application"
})
@EnableJpaRepositories(basePackages = "com.jagt.*.infrastructure.output.persistence.repository")
@EntityScan(basePackages = "com.jagt.*.infrastructure.output.persistence.entity")
public class BankAppModuleApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(BankAppModuleApplication.class, args);
	}

}

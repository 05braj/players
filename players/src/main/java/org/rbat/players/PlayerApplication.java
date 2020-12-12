package org.rbat.players;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
		basePackages = {"org.rbat"})
@EnableCaching
public class PlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerApplication.class, args);
	}

}

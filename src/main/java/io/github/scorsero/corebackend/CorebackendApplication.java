package io.github.scorsero.corebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "db.properties",ignoreResourceNotFound = true)
public class CorebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorebackendApplication.class, args);
	}
}

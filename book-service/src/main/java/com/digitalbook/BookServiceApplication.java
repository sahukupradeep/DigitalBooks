package com.digitalbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}

@RefreshScope
@RestController
class MessageRestController {

	@Value("${message:Hello world - Config Server is not working..Please check configuration }")
	private String message;

	@RequestMapping("/getMessage")
	String getMessage() {
		return this.message;
	}
}

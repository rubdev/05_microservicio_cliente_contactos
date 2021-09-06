package dev.ruben._microservicio_cliente_contactos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Lo necesitaremos en el controller
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}

}

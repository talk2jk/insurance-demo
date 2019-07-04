package no.fremtind.insurance.integrationlayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({})
public class IntegrationLayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationLayerApplication.class, args);
	}
}
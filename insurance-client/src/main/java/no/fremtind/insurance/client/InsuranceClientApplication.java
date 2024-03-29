package no.fremtind.insurance.client;

import no.fremtind.insurance.client.config.ClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ClientProperties.class})
public class InsuranceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceClientApplication.class, args);
    }
}
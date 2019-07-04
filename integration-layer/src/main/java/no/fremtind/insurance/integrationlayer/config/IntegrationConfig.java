package no.fremtind.insurance.integrationlayer.config;

import no.fremtind.insurance.integrationlayer.dto.CustomerDto;
import no.fremtind.insurance.integrationlayer.dto.ContractDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final String EXTERNAL_API_URI = "http://localhost:8088/api";

    @Bean
    @ServiceActivator(inputChannel = "contracts.input")
    public HttpRequestExecutingMessageHandler insuranceCreateOutboundRequest() {

        HttpRequestExecutingMessageHandler handler =
                new HttpRequestExecutingMessageHandler(EXTERNAL_API_URI + "/insurances");
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(ContractDto.class);

        return handler;
    }

    @Bean
    public IntegrationFlow insuranceCustomerOutboundRequest() {
        return IntegrationFlows.from("customers.input")
                .handle(Http.outboundGateway(EXTERNAL_API_URI+ "/customers")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(CustomerDto.class))
                .get();
    }

    @Bean
    public IntegrationFlow insuranceStatusUpdateRequest() {
        return IntegrationFlows.from("contracts.update.inout")
                .handle(Http.outboundGateway(EXTERNAL_API_URI + "/insurances/{contractNumber}/status")
                        .httpMethod(HttpMethod.PUT)
                        .uriVariable("contractNumber", "payload.getContractNumber()")
                        .expectedResponseType(ContractDto.class))
                .get();

    }
}
package no.fremtind.insurance.integrationlayer.config;

import no.fremtind.insurance.common.dtos.ContractDto;
import no.fremtind.insurance.common.dtos.CustomerDto;
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
    @ServiceActivator(inputChannel = "contracts.outbound")
    public HttpRequestExecutingMessageHandler insuranceCreateOutboundRequest() {

        HttpRequestExecutingMessageHandler handler =
                new HttpRequestExecutingMessageHandler(EXTERNAL_API_URI + "/insurances");
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(ContractDto.class);

        return handler;
    }

    @Bean
    public IntegrationFlow insuranceCustomerOutboundRequest() {
        return IntegrationFlows.from("customers.outbound")
                .handle(Http.outboundGateway(EXTERNAL_API_URI+ "/customers")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(CustomerDto.class))
                .get();
    }

    @Bean
    public IntegrationFlow insuranceStatusUpdateRequest() {
        return IntegrationFlows.from("contracts.update.outbound")
                .handle(Http.outboundGateway(EXTERNAL_API_URI + "/insurances/{contractNumber}/status")
                        .httpMethod(HttpMethod.PUT)
                        .uriVariable("contractNumber", "payload.getContractNumber()")
                        .expectedResponseType(ContractDto.class))
                .get();
    }

    @Bean
    public IntegrationFlow contractRetrievalRequest() {
        return IntegrationFlows.from("contracts.retrieve.outbound")
                .handle(Http.outboundGateway(EXTERNAL_API_URI + "/insurances/{contractNumber}")
                        .httpMethod(HttpMethod.GET)
                        .uriVariable("contractNumber", "payload")
                        .expectedResponseType(ContractDto.class))
                .get();

    }
}
package no.fremtind.insurance.integrationlayer.gateway;

import no.fremtind.insurance.integrationlayer.dto.*;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultHeaders = {@GatewayHeader(name = "Content-Type", value = "application/json")})
public interface InsuranceRequestGateway {

    @Gateway(requestChannel = "customers.input")
    CustomerDto createOrGetCustomer(CustomerInfo info);

    @Gateway(requestChannel = "contracts.input")
    ContractDto createInsurance(InsuranceContractRequest request);

    @Gateway(requestChannel = "contracts.update.inout")
    ContractDto updateContractStatus(ContractStatusUpdateDto dto);
}
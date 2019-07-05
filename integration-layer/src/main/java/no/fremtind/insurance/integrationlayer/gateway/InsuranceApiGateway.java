package no.fremtind.insurance.integrationlayer.gateway;

import no.fremtind.insurance.common.dtos.*;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultHeaders = {@GatewayHeader(name = "Content-Type", value = "application/json")})
public interface InsuranceApiGateway {

    @Gateway(requestChannel = "customers.outbound")
    CustomerDto createOrGetCustomer(CustomerInfo info);

    @Gateway(requestChannel = "contracts.outbound")
    ContractDto createInsurance(InsuranceContractRequest request);

    @Gateway(requestChannel = "contracts.update.outbound")
    ContractDto updateContractStatus(ContractStatusUpdateDto dto);

    @Gateway(requestChannel = "contracts.retrieve.outbound")
    ContractDto getContractByContractNumber(String contractNumber);
}
package no.fremtind.insurance.integrationlayer.web.rest;

import lombok.extern.slf4j.Slf4j;
import no.fremtind.insurance.integrationlayer.dto.ContractStatusUpdateDto;
import no.fremtind.insurance.integrationlayer.dto.CustomerDto;
import no.fremtind.insurance.integrationlayer.dto.InsuranceContractRequest;
import no.fremtind.insurance.integrationlayer.dto.ContractDto;
import no.fremtind.insurance.integrationlayer.dto.enums.InsuranceStatus;
import no.fremtind.insurance.integrationlayer.gateway.InsuranceRequestGateway;
import no.fremtind.insurance.integrationlayer.mockservices.MailService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class InsuranceIntegrationResource {

    private final InsuranceRequestGateway gateway;
    private final MailService mailService;

    public InsuranceIntegrationResource(InsuranceRequestGateway gateway, MailService mailService) {
        this.gateway = gateway;
        this.mailService = mailService;
    }

    @PostMapping("/insurance-contracts")
    public ResponseEntity<ContractDto> createInsurance(@RequestBody @Valid InsuranceContractRequest request) {
        log.info("REST request to place Insurance order: {}", request);

        // TODO- retrieve or create a customer
        CustomerDto customer = gateway.createOrGetCustomer(request.getCustomerInfo());
        log.info("Created Customer: {}", customer);

        // create insurance contract
        ContractDto contractResponse =
                gateway.createInsurance(request);
        log.info("Created Contract: {}", contractResponse);

        // send contract to customer
        final String mailContent =
                String.format("Insurance Contract created. Contract Number: [%s] - Status: [%s]",
                        contractResponse.getContractNumber(),
                        String.valueOf(contractResponse.getStatus()));

        mailService.sendMail(
                customer.getEmailAddress(),
                "Your insurance!",
                mailContent,
                false,
                false);

        // Update contract status
        ContractDto updatedContract = gateway
                .updateContractStatus(ContractStatusUpdateDto.builder()
                        .contractNumber(contractResponse.getContractNumber())
                        .status(InsuranceStatus.SENT).build());

        // return contract info (contract Number, contract Status) to client application
        return ResponseEntity.ok(updatedContract);
    }
}
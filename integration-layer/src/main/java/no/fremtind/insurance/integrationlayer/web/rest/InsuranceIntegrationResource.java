package no.fremtind.insurance.integrationlayer.web.rest;

import lombok.extern.slf4j.Slf4j;

import no.fremtind.insurance.common.dtos.ContractDto;
import no.fremtind.insurance.common.dtos.ContractStatusUpdateDto;
import no.fremtind.insurance.common.dtos.CustomerDto;
import no.fremtind.insurance.common.dtos.InsuranceContractRequest;
import no.fremtind.insurance.common.dtos.enums.InsuranceStatus;
import no.fremtind.insurance.integrationlayer.gateway.InsuranceApiGateway;
import no.fremtind.insurance.integrationlayer.mockservices.MailService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api")
public class InsuranceIntegrationResource {

    private final InsuranceApiGateway gateway;
    private final MailService mailService;

    public InsuranceIntegrationResource(InsuranceApiGateway gateway, MailService mailService) {
        this.gateway = gateway;
        this.mailService = mailService;
    }

    @PostMapping("/insurance-contracts")
    public ResponseEntity<ContractDto> createInsurance(@RequestBody @Valid InsuranceContractRequest request) {
        log.info("REST request to create Insurance contract: {}", request);

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
        return ResponseEntity
                .created(URI.create("/api/insurance-contracts/"+ updatedContract.getContractNumber()))
                .body(updatedContract);
    }

    @GetMapping("/insurance-contracts/{contractNumber}")
    public ResponseEntity<ContractDto> getByContractNumber(@PathVariable String contractNumber) {
        log.info("REST request to retrieve an insurance-contract resource with ID: {}", contractNumber);

        return ResponseEntity.ok(gateway.getContractByContractNumber(contractNumber));
    }
}
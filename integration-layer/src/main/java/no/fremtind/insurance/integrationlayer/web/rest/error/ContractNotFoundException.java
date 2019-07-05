package no.fremtind.insurance.integrationlayer.web.rest.error;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(String contractNumber) {
        super(String.format("Insurance Contract with ID %s Not Found", contractNumber));
    }
}

package no.fremtind.insurance.client.service;

import no.fremtind.insurance.common.dtos.ContractDto;
import no.fremtind.insurance.common.dtos.InsuranceContractRequest;

public interface InsuranceService {
    ContractDto createInsuranceContract(InsuranceContractRequest request);
    ContractDto findInsuranceContractByNumber(String contractNumber);
}

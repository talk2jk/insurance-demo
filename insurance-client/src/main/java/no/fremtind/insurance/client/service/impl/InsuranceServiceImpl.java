package no.fremtind.insurance.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import no.fremtind.insurance.client.config.ClientProperties;
import no.fremtind.insurance.client.service.InsuranceService;
import no.fremtind.insurance.client.service.utils.ApiUtils;
import no.fremtind.insurance.common.dtos.ContractDto;
import no.fremtind.insurance.common.dtos.InsuranceContractRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final RestTemplate restTemplate;
    private final ClientProperties clientProperties;

    public InsuranceServiceImpl(RestTemplateBuilder restTemplateBuilder, ClientProperties clientProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.clientProperties = clientProperties;
    }

    @Override
    public ContractDto createInsuranceContract(InsuranceContractRequest request) {
        log.info("Request to create a new insurance contract: {}", request);

        final String resourceUrl =
                String.format("%s/insurances", clientProperties.getIntegrationLayer().getBaseUri());

        ResponseEntity<ContractDto> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.POST,
                ApiUtils.createRequestEntity(request, MediaType.APPLICATION_JSON),
                new ParameterizedTypeReference<ContractDto>() {});

        return response.getBody();
    }

    @Override
    public ContractDto findInsuranceContractByNumber(String contractNumber) {
        log.info("Request to retrieve insurance contract: {}", contractNumber);

        final String resourceUrl =
                String.format("%s/insurances/%s", clientProperties.getIntegrationLayer().getBaseUri(), contractNumber);

        ResponseEntity<ContractDto> entity =
                restTemplate.getForEntity(resourceUrl, ContractDto.class);

        return entity.getBody();
    }
}
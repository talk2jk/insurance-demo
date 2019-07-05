package no.fremtind.insurance.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.fremtind.insurance.client.service.InsuranceService;

import no.fremtind.insurance.common.dtos.ContractDto;
import no.fremtind.insurance.common.dtos.CustomerInfo;
import no.fremtind.insurance.common.dtos.InsuranceContractRequest;
import no.fremtind.insurance.common.dtos.enums.InsuranceStatus;
import no.fremtind.insurance.common.dtos.enums.InsuranceType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withCreatedEntity;

@RunWith(SpringRunner.class)
@RestClientTest(InsuranceServiceImpl.class)
public class InsuranceServiceIntTest {

    private static final String API_BASE_URL = "http://localhost:8088/api";
    private static final String DEFAULT_CONTRACT_NUMBER = "NNNNNNNN";
    private static final String DEFAULT_CUSTOMER_NUMBER = "CCCCCCCC";
    private static final InsuranceStatus DEFAULT_CONTRACT_STATUS = InsuranceStatus.CREATED;
    private static final InsuranceType DEFAULT_CONTRACT_TYPE = InsuranceType.PROTERTY_INSURANCE;


    @Autowired
    private InsuranceService service;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    private ContractDto contractDto;
    private InsuranceContractRequest contractRequest;

    @Before
    public void setUp() throws Exception {

        contractRequest = InsuranceContractRequest.builder()
                .customerInfo(CustomerInfo.builder().firstName("NNNN").lastName("LLLL").emailAddress("email@email.com").socialSecurityNumber("12345678909").build())
                .insuranceType(DEFAULT_CONTRACT_TYPE)
                .build();

        contractDto = ContractDto.builder()
                .contractNumber(DEFAULT_CONTRACT_NUMBER)
                .customerNumber(DEFAULT_CUSTOMER_NUMBER)
                .status(DEFAULT_CONTRACT_STATUS)
                .type(DEFAULT_CONTRACT_TYPE)
                .creationDate(LocalDateTime.now())
                .expiryDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void createInsuranceContract() throws Exception {
        this.server.expect(ExpectedCount.once(), requestTo(API_BASE_URL +"/insurances"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withCreatedEntity(URI.create("/api/insurances/"+DEFAULT_CONTRACT_NUMBER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsBytes(contractDto)));

        ContractDto dto = service.createInsuranceContract(contractRequest);
        server.verify();
        assertEquals(dto, contractDto);
    }

    @Test
    public void findInsuranceContractByNumber() throws Exception {

        String json = objectMapper.writeValueAsString(contractDto);
        server.expect(requestTo(URI.create(API_BASE_URL +"/insurances/"+ DEFAULT_CONTRACT_NUMBER)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));


        ContractDto dto = service.findInsuranceContractByNumber(DEFAULT_CONTRACT_NUMBER);

        assertEquals(dto.getContractNumber(), contractDto.getContractNumber());
        assertEquals(dto.getCustomerNumber(), contractDto.getCustomerNumber());
        assertEquals(dto.getStatus(), contractDto.getStatus());
    }

    private String generateRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
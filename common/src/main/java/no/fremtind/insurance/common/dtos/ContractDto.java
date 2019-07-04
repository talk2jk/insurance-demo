package no.fremtind.insurance.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.fremtind.insurance.common.dtos.enums.InsuranceStatus;
import no.fremtind.insurance.common.dtos.enums.InsuranceType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private String contractNumber;
    private InsuranceType type;
    private InsuranceStatus status;

    private String customerNumber;
}
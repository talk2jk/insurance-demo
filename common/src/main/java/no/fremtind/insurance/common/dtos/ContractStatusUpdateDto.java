package no.fremtind.insurance.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.fremtind.insurance.common.dtos.enums.InsuranceStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractStatusUpdateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    private InsuranceStatus status;
    private String contractNumber;
}

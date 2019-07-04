package no.fremtind.insurance.common.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.fremtind.insurance.common.dtos.enums.InsuranceType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class InsuranceContractRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Valid
    private CustomerInfo customerInfo;

    @NotNull
    private InsuranceType insuranceType;
}
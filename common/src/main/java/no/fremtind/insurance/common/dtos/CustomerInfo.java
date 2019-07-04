package no.fremtind.insurance.common.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CustomerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String emailAddress;

    @NotNull
    @Size(min = 11, max = 11)
    private String socialSecurityNumber;
}

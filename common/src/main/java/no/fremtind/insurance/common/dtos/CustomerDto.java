package no.fremtind.insurance.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerNumber;
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
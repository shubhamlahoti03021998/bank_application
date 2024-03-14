package com.ProjectMicroservices.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Account",
        description = "Customer's Account Information"
)
public class AccountsDTO {

    @Schema(
            description = "AccountNumber of the Customer", example = "1003052880"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "AccountType of the Customer", example = "SAVINGS/CURRENT"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            description = "Branch Address of the Bank", example = "D1905, Roseville, Network"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;

}

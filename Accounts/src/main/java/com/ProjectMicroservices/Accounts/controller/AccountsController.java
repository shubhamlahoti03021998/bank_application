package com.ProjectMicroservices.Accounts.controller;

import com.ProjectMicroservices.Accounts.constants.AccountsConstants;
import com.ProjectMicroservices.Accounts.dto.CustomerDTO;
import com.ProjectMicroservices.Accounts.dto.ErrorResponseDTO;
import com.ProjectMicroservices.Accounts.dto.ResponseDTO;
import com.ProjectMicroservices.Accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "CRUD Rest Endpoints for Accounts Microservice in Bank Application",
        description = "GET, POST, PUT, DELETE Endpoints for account details"
)
    @RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE} )
@Validated
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    /**
     * This is used to create/add customer to Database
     * @param customerDTO customerDTO
     * @return a Response Entity object which contains status code, Response body
     */

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside Bank"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                           description = "HTTP Status CREATED",
                            content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
        accountsService.createNewAccount(customerDTO);
        System.out.println(accountsService.getClass().getName());
       return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"

    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
            )
    }
    )
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<CustomerDTO> fetchAccountsDetails(@RequestParam(required = true)
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")String mobileNumber){
        CustomerDTO accountDetails = accountsService.getAccountDetails(mobileNumber);
        return new ResponseEntity<>(accountDetails, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@RequestBody CustomerDTO customerDTO){
        boolean b = accountsService.updateAccount(customerDTO);
        if(b){
            return ResponseEntity.status(HttpStatus.OK).
            body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                    body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@PathVariable  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                String mobileNumber){
        boolean b = accountsService.deleteAccount(mobileNumber);
        if(b){
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                    body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
    }
}

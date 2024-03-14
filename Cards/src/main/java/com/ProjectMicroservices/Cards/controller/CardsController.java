package com.ProjectMicroservices.Cards.controller;

import com.ProjectMicroservices.Cards.constants.CardConstants;
import com.ProjectMicroservices.Cards.dto.CardsContactInfoDTO;
import com.ProjectMicroservices.Cards.dto.CardsDTO;
import com.ProjectMicroservices.Cards.dto.ErrorReponseDTO;
import com.ProjectMicroservices.Cards.dto.ResponseDTO;
import com.ProjectMicroservices.Cards.service.CardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD Rest Endpoints for Cards Microservice in Bank Application",
        description = "CRUD REST APIs in Bank Application to CREATE, UPDATE, FETCH AND DELETE Cards details"
)
@Validated
public class CardsController {

    private CardsService cardsService;

    @Autowired
    public CardsController(CardsService cardsService){
        this.cardsService = cardsService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDTO cardsContactInfoDTO;


    @PostMapping("/save")
    @Operation(
            summary = "Create Card REST API through Cards Object",
            description = "REST API to create new Card inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    }
    )
    public ResponseEntity<ResponseDTO> createCard(@RequestBody CardsDTO cardsDTO){
        cardsService.createCard(cardsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(CardConstants.STATUS_201,CardConstants.MESSAGE_201));
    }


    @PostMapping("/save/{mobileNumber}")
    @Operation(
            summary = "Create Card REST API through Mobile",
            description = "REST API to create new Card inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    }
    )
    public ResponseEntity<ResponseDTO> createNewCard(@Valid @PathVariable
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                     String mobileNumber){
        cardsService.createNewCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(CardConstants.STATUS_201,CardConstants.MESSAGE_201));
    }

    @GetMapping("/fetch/{mobileNumber}")
    @Operation(
            summary = "Fetch Card REST API through Mobile",
            description = "REST API to fetch existing Card inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    })
    public ResponseEntity<CardsDTO> getCardDetails(@Valid @PathVariable
                                                          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")String mobileNumber){
        return new ResponseEntity<>(cardsService.getCardDetailsbyMobile(mobileNumber), HttpStatus.OK);
    }

    @GetMapping("/cards/all")
    @Operation(
            summary = "Fetch all existing Cards REST API",
            description = "REST API to fetch all existing Cards inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<CardsDTO>> getAllCards(){
        List<CardsDTO> cardsDTOS = cardsService.fetchAllExistingCards();
        return new ResponseEntity<>(cardsDTOS, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update Card REST API through Mobile",
            description = "REST API to update existing Card details inside Bank"
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
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    })
    public ResponseEntity<ResponseDTO> updateCardDetails(@RequestBody CardsDTO cardsDTO){
        boolean b = cardsService.updateCardDetails(cardsDTO);
        if(b){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Hibernate: delete from cards where card_id=?
     * @param mobileNumber
     * @return
     */

    @DeleteMapping("/delete/{mobileNumber}")
    @Operation(
            summary = "Delete Card REST API through Mobile",
            description = "REST API to delete existing Card details inside Bank"
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
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    })
    public ResponseEntity<ResponseDTO> deleteCardDetails(@Valid @PathVariable
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){

        boolean isDeleted = cardsService.deleteCardDetails(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                        .body(new ResponseDTO(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/maven-version")
    @Operation(
            summary = "Fetch the current maven version"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    })
    public ResponseEntity<String> getMavenVersion(){
        return new ResponseEntity<>(environment.getProperty("MAVEN_HOME"),HttpStatus.OK);
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorReponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDTO> getContactInfo(){
        return new ResponseEntity<>(cardsContactInfoDTO, HttpStatus.OK);
    }

}

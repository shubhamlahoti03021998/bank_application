package com.ProjectMicroservices.Cards.dto;

import com.ProjectMicroservices.Cards.entity.Cards;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@Schema(name = "Cards",
        description = "Schema to hold Card information"
)
public class CardsDTO {

    @NotNull(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
           description = "Mobile Number of Customer", example = "4354437687"
    )
    private String mobileNumber;

    @NotNull(message = "Card Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile number must be 102 digits")
    @Schema(
            description = "Card Number of Customer", example = "435443768745"
    )
    private String cardNumber;

    @NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
            description = "Type of the card", example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total amount limit available against a card", example = "100000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "90000"
    )
    private int availableAmount;

    public CardsDTO(Cards cards) {
        this.amountUsed= cards.getAmountUsed();
        this.mobileNumber = cards.getMobileNumber();
        this.cardNumber = cards.getCardNumber();
        this.cardType = cards.getCardType();
        this.totalLimit = cards.getTotalLimit();
        this.availableAmount = cards.getAvailableAmount();
    }
}

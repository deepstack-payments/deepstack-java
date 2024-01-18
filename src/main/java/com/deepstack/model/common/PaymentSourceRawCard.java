package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import com.deepstack.model.CreditCard;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Data
@AllArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PaymentSourceRawCard extends PaymentSource {

    {
        Type = PaymentSourceType.CREDIT_CARD;
    }

    /**
     * Card information
     */
    @JsonProperty(value = "credit_card")
    private CreditCard CreditCard;

}

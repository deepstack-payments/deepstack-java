package com.deepstack.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Payment Source for tokens/Payment instruments
 */
@Data
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PaymentSourceCardOnFile extends PaymentSource{

    {
        Type = PaymentSourceType.CARD_ON_FILE;
    }

    /**
     * Token Information
     */
    @JsonProperty(value = "card_on_file", required = true)
    private CardOnFile CardOnFile;
}

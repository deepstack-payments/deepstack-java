package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public class CardOnFile {

    /**
     * Token/Payment Instrument value
     */
    @JsonProperty(value = "id", required = true)
    private String Id;

    @Builder.Default
    @JsonProperty(value = "customer_id")
    private String CustomerId = "Deprecated";

    /**
     * CVV
     */
    @JsonProperty(value = "cvv")
    private String CVV;
}

package com.deepstack.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentSourceType {

    @JsonProperty("credit_card")
    CREDIT_CARD,

    @JsonProperty("card_on_file")
    CARD_ON_FILE

}

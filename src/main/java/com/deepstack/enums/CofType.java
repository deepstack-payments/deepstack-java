package com.deepstack.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CofType {

    @JsonProperty("UNSCHEDULED_CARDHOLDER")
    UNSCHEDULED_CARDHOLDER,

    @JsonProperty("UNSCHEDULED_MERCHANT")
    UNSCHEDULED_MERCHANT,

    @JsonProperty("RECURRING")
    RECURRING,

    @JsonProperty("INSTALLMENT")
    INSTALLMENT

}

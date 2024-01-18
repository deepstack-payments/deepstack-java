package com.deepstack.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class PaymentInstrument {

    @JsonProperty(value = "id")
    protected String Id;
    @JsonProperty(value = "type")
    protected PaymentSourceType Type;

}

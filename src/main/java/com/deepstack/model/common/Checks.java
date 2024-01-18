package com.deepstack.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Checks values for charge responses
 */
@Data
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checks {

    @JsonProperty(value = "address_line1_check")
    private String AddressLine1Check;

    @JsonProperty(value = "address_postal_code_check")
    private String AddressPostalCodeCheck;

    @JsonProperty(value = "cvc_check")
    private String CvcCheck;

}

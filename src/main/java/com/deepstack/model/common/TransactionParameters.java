package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.CofType;
import com.deepstack.model.Entity;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Transaction information
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TransactionParameters extends Entity {

    /**
     * Amount in cent amount ($5.99 => 599)
     */
    @JsonProperty(value = "amount", required = true)
    private Integer Amount;

    /**
     * Whether to capture the charge or not (default true)
     */
    @Builder.Default
    @JsonProperty(value="capture", required = true)
    private boolean Capture = true;

    /**
     * CoF type (default is Unscheduled Cardholder)
     */
    @Builder.Default
    @JsonProperty(value="cof_type")
    private CofType cofType = CofType.UNSCHEDULED_CARDHOLDER;

    /**
     * ISO3166 3-digit country code. Defaulted to USA
     */
    @Builder.Default
    @JsonProperty(value="country_code")
    private String CountryCode = "USA";

    /**
     * 3 Digit currency code (USD, etc) Defaulted to USD
     */
    @Builder.Default
    @JsonProperty(value="currency_code")
    private String CurrencyCode = "USD";

    /**
     * Use AVS flag (default false)
     */
    @Builder.Default
    @JsonProperty(value="avs")
    private boolean AVS = false;

    /**
     * Save payment source as Payment Instrument (default false)
     */
    @Builder.Default
    @JsonProperty(value = "save_payment_instrument")
    private boolean SavePaymentInstrument = false;

    @JsonProperty(value = "kount_session_id")
    private String KountSessionId;
}

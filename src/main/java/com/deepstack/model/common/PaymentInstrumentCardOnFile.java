package com.deepstack.model.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import com.deepstack.model.BillingContact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentCardOnFile extends PaymentInstrument{

    {
        Type = PaymentSourceType.CARD_ON_FILE;
    }

    /**
     * Customer ID
     */
    @JsonProperty(value = "customer_id")
    private String CustomerId;

    /**
     * Client ID
     */
    @JsonProperty(value = "client_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientId;

    /**
     * Client customer ID
     */
    @JsonProperty(value = "client_customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientCustomerId;

    /**
     * Brand
     */
    @JsonProperty(value = "brand")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Brand;

    /**
     * BIN (first 6)
     */
    @JsonProperty(value = "bin")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String BIN;

    /**
     * Last four digits of card
     */
    @JsonProperty(value = "last_four")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String LastFour;

    /**
     * Expiration date in (mmYY)
     */
    @JsonProperty(value = "expiration")
    private String Expiration;

    /**
     * Billing information
     */
    @JsonProperty(value = "billing_contact")
    private BillingContact BillingContact;

    /**
     * If payment instrument is the default (future use)
     */
    @JsonProperty(value = "is_default")
    private boolean IsDefault;

    @JsonProperty(value = "v1_legacy_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String V1LegacyToken;

    @JsonProperty(value = "token_ex_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String TokenExToken;

    // Need to flush out object here eventually but not now
    /**
     * CMPI lookup response
     */
    @JsonProperty(value = "cmpi_lookup_response")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CardinalMPI;

    /**
     * CAVV
     */
    @JsonProperty(value = "cavv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CAVV;

    /**
     * ECI Flag
     */
    @JsonProperty(value = "eciflag")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ECIFlag;

    /**
     * XID
     */
    @JsonProperty(value = "xid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Xid;


}

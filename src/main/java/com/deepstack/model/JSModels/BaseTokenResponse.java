package com.deepstack.model.JSModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Base Token Response if token is sent straight from JS SDK
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseTokenResponse {

    /**
     * Token ID value
     */
    @JsonProperty(value = "token")
    private String Id;

//    @JsonProperty(value = "type")
//    private PaymentSourceType Type;

    /**
     * Card brand
     */
    @JsonProperty(value = "brand")
    private String Brand;

    /**
     * Card bin (first 6)
     */
    @JsonProperty(value = "bin")
    private String Bin;

    /**
     * Last four digits of card
     */
    @JsonProperty("lastFour")
    private String LastFour;

    // Company setting if raw pan is returned from the jsSDK
    /**
     * Raw card information if SendRawPan is enabled for the company
     */
    @JsonProperty(value = "pan")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String PAN;

    /**
     * Card expiration in format (mmYY)
     */
    @JsonProperty(value = "expiration")
    private String Expiration;

    // card cvv (returned from the js SDK but not direct api calls)
    /**
     * Card CVV
     */
    @JsonProperty(value = "cvv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CVV;

    /**
     * Billing information (JS SDK version)
     */
    @JsonProperty(value = "billingContact")
    private ClientBillingContact BillingContact;

    /**
     * Shipping address
     */
    @JsonProperty(value = "shippingContact")
    private ClientAddress ShippingContact;

    /**
     * Customer ID
     */
    @JsonProperty("customer_id")
    private String CustomerId;

    /**
     * Default payment instrument (future use)
     */
    @JsonProperty(value = "is_default")
    private boolean IsDefault;

    /**
     * Client Customer ID
     */
    @JsonProperty("client_customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientCustomerId;

    /**
     * Client ID
     */
    @JsonProperty("client_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ClientId;

    /**
     * Created date for response
     */
    @JsonProperty("created")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Created;

    /**
     * Updated date for response
     */
    @JsonProperty("updated")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String Updated;

    @JsonProperty(value = "kountSessionId")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String KountSessionId;

    @JsonProperty(value = "v1_legacy_token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String V1LegacyToken;

    @JsonProperty(value = "tokenExToken")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String TokenExToken;

    // TODO update to object
    @JsonProperty(value = "cmpi_lookup_response")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CardinalMPI;

    @JsonProperty(value = "cavv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CAVV;

    @JsonProperty(value = "eciflag")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ECIFlag;

    @JsonProperty(value = "xid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String XID;

}

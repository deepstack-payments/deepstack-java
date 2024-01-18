package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import com.deepstack.model.BillingContact;
import com.deepstack.model.CreditCard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentCard extends PaymentInstrument{

    {
        Type = PaymentSourceType.CREDIT_CARD;
    }

    /**
     * Customer ID
     */
    @JsonProperty(value = "customer_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    private String ClientCustomerId;

    /**
     * Billing information
     */
    @JsonProperty(value = "billing_contact")
    private BillingContact BillingContact;

    /**
     * Card information
     */
    @JsonProperty(value = "credit_card")
    private CreditCard CreditCard;

    /**
     * CAVV
     */
    @JsonProperty(value = "cavv")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CAVV;

    /**
     * ECI flag
     */
    @JsonProperty(value = "eciflag")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ECIFlag;

    /**
     * XID
     */
    @JsonProperty(value = "xid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String XID;

}

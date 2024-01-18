package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.Interface.IPaymentSource;
import com.deepstack.enums.PaymentSourceType;
import com.deepstack.model.BillingContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 * Payment source for raw card data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@SuperBuilder
public class PaymentSource implements IPaymentSource {

    /**
     * Type of payment source (inherited classes should declare these)
     */
    @JsonProperty(value = "type")
    protected PaymentSourceType Type;

    /**
     * Billing information
     */
    @JsonProperty(value = "billing_contact")
    protected BillingContact BillingContact;

}

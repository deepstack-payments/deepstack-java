package com.deepstack.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.model.ShippingContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Meta information for charge requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMeta {

    /**
     * Client transaction ID
     */
    @JsonProperty(value = "client_transaction_id")
    private String ClientTransactionID;

    /**
     * Client customer ID
     */
    @JsonProperty(value = "client_customer_id")
    private String ClientCustomerID;

    /**
     * Client transaction description
     */
    @JsonProperty(value = "client_transaction_description")
    private String ClientTransactionDescription;

    /**
     * Client Invoice ID
     */
    @JsonProperty(value = "client_invoice_id")
    private String ClientInvoiceID;

    /**
     * Shipping information
     */
    @JsonProperty(value = "shipping_info")
    private ShippingContact ShippingContact;

}

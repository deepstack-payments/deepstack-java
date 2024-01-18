package com.deepstack.model;

import com.fasterxml.jackson.annotation.*;
import com.deepstack.enums.CofType;
import com.deepstack.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeResponse extends Entity {

  /**
   * Transaction ID created for charge request (used in capture/void/refund)
   */
  @JsonProperty(value = "id")
  private String ID;

  /**
   * Deepstack response code
   */
  @JsonProperty("response_code")
  private String ResponseCode;

  /**
   * Deepstack response message
   */
  @JsonProperty(value = "message")
  private String Message;

  /**
   * Flag for whether transaction was approved
   */
  @JsonProperty(value = "approved")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean approved;

  /**
   * Authorization code based on backend processor
   */
  @JsonProperty(value = "auth_code")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AuthCode;

  /**
   * Response from CVV check
   */
  @JsonProperty(value = "cvv_result")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CVVResponse;

  /**
   * Response from AVS check
   */
  @JsonProperty(value = "avs_result")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AVSResponse;

  /**
   * Scrubbed echo of payment instrument used
   */
  @JsonProperty(value = "source")
  private PaymentInstrumentCard Source;

  /**
   * Charge amount
   */
  @JsonProperty(value = "amount")
  private Integer Amount;

  /**
   * Flag to indicate if transaction was captured
   */
  @JsonProperty(value = "captured")
  private boolean Captured;

  /**
   * COF type (UNSCHEDULED_CARDHOLER, etc)
   */
  @JsonProperty(value = "cof_type")
  private CofType CofType;

  /**
   * Currency code (USD, etc)
   */
  // TODO switch this to an enum eventually
  @JsonProperty(value = "currency_code")
  private String CurrencyCode;

  /**
   * ISO3166 3-digit country code
   */
  @JsonProperty(value = "country_code")
  private String CountryCode;

  /**
   * Billing information
   */
  @JsonProperty(value = "billing_info")
  private BillingContact BillingInfo;

  /**
   * Flag to indicate whether a PaymentInstrument should be created in the response
   */
  @JsonProperty(value = "save_payment_instrument")
  private boolean SavePaymentInstrument;

  /**
   * Information about new payment instrument created (ID will be the payment instrument ID)
   */
  @JsonProperty("new_payment_instrument")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private PaymentInstrumentCardOnFile paymentInstrument;

  /**
   * Kount result
   */
  @JsonProperty(value = "kount_score")
  private String KountScore;

  /**
   * Client transaction ID
   */
  @JsonProperty(value = "client_transaction_id")
  private String ClientTransactionID;

  /**
   * Client Customer ID
   */
  @JsonProperty(value = "client_customer_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
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
   * Shipping information for order
   */
  @JsonProperty(value = "shipping_info")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ShippingContact ShippingContact;

  /**
   * Various check results (dependent on backend processor)
   */
  @JsonProperty(value = "checks")
  private Checks Checks;

  /**
   * Completed timestamp
   */
  @JsonProperty(value = "completed")
  private String Completed;

}

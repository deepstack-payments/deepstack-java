package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.model.common.PaymentSource;
import com.deepstack.model.common.TransactionMeta;
import com.deepstack.model.common.TransactionParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeRequest extends Entity {
  public static final String DEFAULT_CLIENT_TRANSACTION_ID = "000000000";
  public static final String DEFAULT_CLIENT_TRANSACTION_DESC = "No Description provided!";
  public static final String DEFAULT_CLIENT_INVOICE_ID = "000000";

  /**
   * Information about instrument used for transaction
   */
  @JsonProperty(value = "source", required = true)
  private PaymentSource Source;

  /**
   * Information about the transaction (amount, currency, etc)
   */
  @JsonProperty(value = "transaction")
  private TransactionParameters Params;

  /**
   * Meta information (typically merchant side variables)
   */
  @JsonProperty(value = "meta")
  private TransactionMeta Meta;
}

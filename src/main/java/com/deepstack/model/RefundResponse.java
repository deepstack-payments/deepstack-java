package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundResponse extends Entity {

  /**
   * Original charge transaction ID
   */
  @JsonProperty("charge_transaction_id")
  private String ChargeTransactionID;

  /**
   * Amount refunded in cent amount (voids are always the entire amount)
   */
  @JsonProperty(value = "amount")
  private Integer Amount;

  /**
   * ID created with refund request (not always returned depending on processor)
   */
  @JsonProperty(value = "id")
  private String Id;

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

  // This is ignored when deepstack sends the response
//  private String Response;

  /**
   * Approved flag
   */
  @JsonProperty(value = "approved")
  private boolean Approved;

  /**
   * Authorization code based on backend processor
   */
  @JsonProperty("auth_code")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AuthCode;

  /**
   * Resposne from CVV check
   */
  @JsonProperty("cvv_response")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CvvResponse;

  /**
   * Response from AVS check
   */
  @JsonProperty("avs_response")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String AvsResponse;

  @JsonProperty(value = "processor_transaction_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ProcessorTransactionID;

  /**
   * Completed timestamp of transaction
   */
  @JsonProperty(value = "completed")
  private String Completed;
}

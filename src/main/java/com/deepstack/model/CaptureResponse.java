package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CaptureResponse extends Entity {

  /**
   * Original charge transaction ID
   */
  @JsonProperty("charge_transaction_id")
  private String ChargeTransactionID;

  /**
   * Amount captured in cent amount
   */
  private Integer Amount;

  /**
   * Whether the amount captured is recurring
   */
  @JsonProperty("recurring")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean isRecurring;

  /**
   * ID created with capture request (not always returned depending on processor)
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

  //This is ignored when deepstack sends the response
//  private String Response;

  /**
   * Flag for whether transaction was approved
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
   * Response from CVV check
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

  /**
   * Processor transaction ID (not returned)
   */
  @JsonProperty(value = "processor_transaction_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ProcessorTransactionID;

  /**
   * Completed timestamp of transaction
   */
  @JsonProperty(value = "completed")
  private String Completed;
}

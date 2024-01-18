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

/**
 * Refund request object
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundRequest extends Entity {

  /**
   * Original charge transaction ID
   */
  @JsonProperty(value = "charge")
  private String Charge;

  /**
   * Amount to refund in cent amount (voids will always void the entire transaction)
   */
  @JsonProperty(value = "amount")
  private Integer Amount;

  /**
   * Reason for request
   */
  @JsonProperty(value = "reason")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Reason;

  // Don't see it required on deepstack
//  private String reason;
//  private String id;
//  private String clientId;
}

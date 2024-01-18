package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CaptureRequest extends Entity {

  /**
   * Original transaction ID to be captured
   */
  @JsonProperty(value = "charge")
  private String Charge;

  /**
   * Amount to capture in cent amount
   */
  @JsonProperty(value = "amount")
  private Integer Amount;
}

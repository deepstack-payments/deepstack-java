package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard {

  /**
   * Card number
   */
  @JsonProperty(value = "account_number")
  private String Number;

  /**
   * Expiration in format (mmYY)
   */
  @JsonProperty(value = "expiration")
  private String Expiration;

  /**
   * Card CVV
   */
  @JsonProperty(value = "cvv")
  private String CVV;

  /**
   * Card brand (not required)
   */
  @JsonProperty(value = "brand")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Brand;

  /**
   * Card last four (not required)
   */
  @JsonProperty("last_four")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String LastFour;

  /**
   * Card bin (not required)
   */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String bin;
}

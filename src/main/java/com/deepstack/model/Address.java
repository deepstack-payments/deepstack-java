package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Deepstack billing address object
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

  /**
   * Line 1 of billing address
   */
  @JsonProperty(value = "line_1")
  private String Line1;

  /**
   * Line 2 of billing address
   */
  @JsonProperty(value = "line_2")
  private String Line2;

  /**
   * City of billing address
   */
  @JsonProperty(value = "city")
  private String City;

  /**
   * State of billing address
   */
  @JsonProperty(value = "state")
  private String State;

  /**
   * Postal code of billing address
   */
  @JsonProperty(value = "postal_code")
  private String PostalCode;

  /**
   * ISO3166 3-letter code for country (int value should also be accepted)
   */
  @JsonProperty(value = "country_code")
  private String CountryCode;
}

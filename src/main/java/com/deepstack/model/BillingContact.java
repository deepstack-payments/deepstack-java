package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deepstack billing information including address
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingContact {

  /**
   * Billing first name
   */
  @JsonProperty(value = "first_name")
  private String FirstName;

  /**
   * Billing last name
   */
  @JsonProperty(value = "last_name")
  private String LastName;

  /**
   * Billing Address object
   */
  @JsonProperty(value = "address")
  private Address Address;

  /**
   * Billing cardholder phone number
   */
  @JsonProperty(value = "phone")
  private String Phone;

  /**
   * Billing cardholder email address
   */
  @JsonProperty(value = "email")
  private String Email;
}

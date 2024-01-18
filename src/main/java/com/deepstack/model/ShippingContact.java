package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ShippingContact {

    /**
     * Shipping first name
     */
    @JsonProperty(value = "first_name")
    private String FirstName;

    /**
     * Shipping last name
     */
    @JsonProperty(value = "last_name")
    private String LastName;

    /**
     * Shipping Address
     */
    @JsonProperty(value = "address")
    private Address Address;

    /**
     * Shipping phone
     */
    @JsonProperty(value = "phone")
    private String Phone;

    /**
     * Shipping email
     */
    @JsonProperty(value = "email")
    private String Email;

}

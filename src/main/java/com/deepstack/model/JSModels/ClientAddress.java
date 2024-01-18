package com.deepstack.model.JSModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Separate address object used by JS SDK
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddress {

    /**
     * Line 1
     */
    @JsonProperty(value = "lineOne")
    private String Line1;

    /**
     * Line 2
     */
    @JsonProperty(value = "lineTwo")
    private String Line2;

    /**
     * City
     */
    @JsonProperty(value = "city")
    private String City;

    /**
     * State
     */
    @JsonProperty(value = "state")
    private String State;

    /**
     * Postal code (zip code)
     */
    @JsonProperty(value = "postalCode")
    private String PostalCode;

    /**
     * ISO3166 3-digit country code
     */
    @JsonProperty(value = "country")
    private String CountryCode;

}

package com.deepstack.model.JSModels;


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
public class ClientBillingContact {

    @JsonProperty(value = "firstName")
    private String FirstName;

    @JsonProperty(value = "lastName")
    private String LastName;

    @JsonProperty(value = "address")
    private ClientAddress Address;

    @JsonProperty(value = "phone")
    private String Phone;

    @JsonProperty(value = "email")
    private String Email;

}

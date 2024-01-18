package com.deepstack.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
//@SuperBuilder
public class FrontEndRequest extends Entity{

    @JsonProperty(value = "id")
    private String ID;

    @JsonProperty("response_code")
    private String ResponseCode;

    @JsonProperty(value = "message")
    private String Message;

    @JsonProperty(value = "approved")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean approved;

    @JsonProperty(value = "auth_code")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String AuthCode;

    @JsonProperty(value = "cvv_result")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String CVVResponse;

    @JsonProperty(value = "avs_result")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String AVSResponse;

    @JsonProperty(value = "completed")
    private String Completed;


}

package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.enums.PaymentSourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Token response object from Transactions API
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrumentToken extends Entity {

  /**
   *
   */
  @JsonProperty(value = "id")
  private String Id;

  @JsonProperty(value = "type")
  private PaymentSourceType Type;

  @JsonProperty(value = "brand")
  private String Brand;

  @JsonProperty(value = "bin")
  private String Bin;

  @JsonProperty("last_four")
  private String LastFour;

  // Company setting if raw pan is returned from the jsSDK
  @JsonProperty(value = "pan")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String PAN;

  @JsonProperty(value = "expiration")
  private String Expiration;

  // card cvv (returned from the js SDK but not direct api calls)
  @JsonProperty(value = "cvv")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CVV;

  @JsonProperty("billing_contact")
  private BillingContact BillingContact;

  @JsonProperty("customer_id")
  private String CustomerId;

  @JsonProperty(value = "is_default")
  private boolean IsDefault;

  @JsonProperty("client_customer_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ClientCustomerId;

  @JsonProperty("client_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ClientId;

  @JsonProperty("created")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Created;

  @JsonProperty("updated")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Updated;

  @JsonProperty(value = "kount_session_id")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String KountSessionId;

  @JsonProperty(value = "v1_legacy_token")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String V1LegacyToken;

  @JsonProperty(value = "token_ex_token")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String TokenExToken;

  // TODO update to object
  @JsonProperty(value = "cmpi_lookup_response")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CardinalMPI;

  @JsonProperty(value = "cavv")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CAVV;

  @JsonProperty(value = "eciflag")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ECIFlag;

  @JsonProperty(value = "xid")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String XID;
}

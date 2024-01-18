package com.deepstack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.model.common.PaymentInstrumentCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Request for creating tokens from raw cards
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequest extends Entity {

  /**
   * Card information for token
   */
  @JsonProperty("payment_instrument")
  private PaymentInstrumentCard PaymentInstrumentRequest;

  /**
   * Kount information
   */
  @JsonProperty("kount_session_id")
  private String kountSessionId;

  /**
   * CAVV
   */
  @JsonProperty(value = "cavv")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String CAVV;

  /**
   * ECI Flag
   */
  @JsonProperty(value = "eciflag")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String ECIFlag;

  /**
   * XID
   */
  @JsonProperty(value = "xid")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String XID;

  /**
   * Whether 3DS flag
   */
  @Builder.Default
  @JsonProperty(value = "requires_3ds")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private boolean Requires3DS = false;

  // Change to object later
  @JsonProperty(value = "order")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Order;

  @JsonProperty(value = "jwt")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String Jwt;
}

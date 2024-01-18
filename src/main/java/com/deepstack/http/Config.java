package com.deepstack.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Config {
  private String publishableApiKey;
  private String appId;
  private String sharedSecret;
  private String sandbox;
}

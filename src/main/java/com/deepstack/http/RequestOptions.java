package com.deepstack.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RequestOptions {

  private final int connectTimeout;
  private final int readTimeout;
  private String publishableApiKey;
  private String appId;
  private String sharedSecret;
  private final int maxNetworkRetries;

  public static RequestOptions getDefault() {
    return RequestOptions.builder()
        .connectTimeout(BasicInterface.getConnectTimeout())
        .readTimeout(BasicInterface.getReadTimeout())
        .maxNetworkRetries(BasicInterface.getMaxNetworkRetries())
        .build();
  }
}

package com.deepstack.http;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/** A request to GloballyPaid's API. */
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Request {

  @Setter @Getter private Method method;

  @Setter @Getter private String baseUri;

  @Setter @Getter private String endpoint;

  @Setter @Getter private String body;

  @Setter @Getter private Map<String, String> headers = new HashMap();

  @Getter private Map<String, String> queryParams = new HashMap();

  @Setter private RequestOptions options;

  @Setter(AccessLevel.PACKAGE)
  @Getter(AccessLevel.PACKAGE)
  private boolean nonZeroCheck;

  public Request() {
    this.reset();
  }

  public void reset() {
    this.clearMethod();
    this.clearBaseUri();
    this.clearEndpoint();
    this.clearBody();
    this.clearHeaders();
    this.clearQueryParams();
  }

  public void addQueryParam(String key, String value) {
    this.queryParams.put(key, value);
  }

  public void addHeader(String key, String value) {
    this.headers.put(key, value);
  }

  public void clearMethod() {
    this.method = null;
  }

  public void clearBaseUri() {
    this.baseUri = "";
  }

  public void clearEndpoint() {
    this.endpoint = "";
  }

  public void clearBody() {
    this.body = "";
  }

  public void clearQueryParams() {
    this.queryParams.clear();
  }

  public void clearHeaders() {
    this.headers.clear();
  }

  public RequestOptions getOptions() {
    return (options != null) ? options : RequestOptions.getDefault();
  }
}

package com.deepstack.http;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/** Class Response provides a standard interface to an GloballyPaid API's HTTP request. */
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Response {
  /** The HTTP status code of the response. */
  private int statusCode;

  /** The body of the response. */
  private String body;

  /** The HTTP headers of the response. */
  private Map<String, String> headers;

  /** Number of times the request was retried. Used for internal tests only. */
  private int numRetries;

  public Response() {
    this.reset();
  }

  public void reset() {
    this.statusCode = 0;
    this.body = "";
    this.headers = null;
  }
}

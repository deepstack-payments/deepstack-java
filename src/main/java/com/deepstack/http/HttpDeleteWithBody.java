package com.deepstack.http;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/** A wrapper to {@link org.apache.http.HttpRequest} DELETE method to accept a request body. */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
  public static final String METHOD_NAME = "DELETE";

  public String getMethod() {
    return "DELETE";
  }

  public HttpDeleteWithBody(String uri) {
    this.setURI(URI.create(uri));
  }
}

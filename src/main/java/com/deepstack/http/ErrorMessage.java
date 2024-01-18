package com.deepstack.http;

public enum ErrorMessage {
  BAD_REQUEST("Your request is invalid."),
  UNAUTHORIZED("Your API key is wrong."),
  FORBIDDEN("The endpoint requested is hidden for administrators only."),
  NOT_FOUND("The specified endpoint could not be found."),
  METHOD_NOT_ALLOWED("You tried to access a request with an invalid method."),
  NOT_ACCEPTABLE("You requested a format that isn't json."),
  GONE("The endpoint requested has been removed from our servers."),
  RATE_LIMIT_EXCEEDED("Your application is making too many requests! Slow down!"),
  INTERNAL_SERVER_ERROR("We had a problem with our server. Try again later."),
  SERVICE_UNAVAILABLE("We're temporarily offline for maintenance. Please try again later.");

  public final String label;

  private ErrorMessage(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}

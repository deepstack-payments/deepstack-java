package com.deepstack.exception;

public class RateLimitException extends DeepStackException {

  public RateLimitException(Integer code, String message, Throwable e) {
    super(code, message, e);
  }
}

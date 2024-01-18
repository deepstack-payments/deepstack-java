package com.deepstack.exception;

public class ApiException extends DeepStackException {

  public ApiException(Integer code, String message, Throwable e) {
    super(code, message, e);
  }
}

package com.deepstack.exception;

public class ForbiddenException extends DeepStackException {

  public ForbiddenException(Integer code, String message, Throwable e) {
    super(code, message, e);
  }
}

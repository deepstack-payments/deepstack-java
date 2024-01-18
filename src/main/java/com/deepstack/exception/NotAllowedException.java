package com.deepstack.exception;

public class NotAllowedException extends DeepStackException {

  public NotAllowedException(Integer code, String message) {
    super(code, message);
  }
}

package com.deepstack.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends DeepStackException {
  private final String param;

  public InvalidRequestException(Integer code, String message, String param, Throwable e) {
    super(code, message, e);
    this.param = param;
  }
}

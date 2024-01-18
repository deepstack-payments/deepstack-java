package com.deepstack.exception;

import com.deepstack.util.JsonUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.http.util.TextUtils;

@Getter
public abstract class DeepStackException extends Exception {
  private static final long serialVersionUID = 2L;

  /** The error resource returned by GloballyPaid's API that caused the exception. */
  //  @Setter
  private Map<String, Object> deepStackError = new HashMap<>();

  private Integer code;
  private final String message;

  public DeepStackException(final Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public DeepStackException(String message, Throwable e) {
    super(message, e);
    this.message = message;
  }

  public DeepStackException(String message) {
    super(message);
    this.message = message;
  }

  protected DeepStackException(Integer code, String message, Throwable e) {
    super(message, e);
    this.code = code;
    this.message = message;
  }

  public Map<String, Object> setDeepStackError(String deepStackError) throws IOException {
    this.deepStackError =
        TextUtils.isBlank(deepStackError)
            ? new HashMap<>()
            : JsonUtils.convertFromJsonToObject(deepStackError, Map.class);
    return this.deepStackError;
  }

  //  /**
  //   * Returns a description of the exception, including the HTTP status code.
  //   *
  //   * @return a string representation of the exception.
  //   */
  //  @Override
  //  public String getMessage() {
  //    return String.format(
  //        "Status code: [%d]"
  //            + (TextUtils.isBlank(message) ? "" : ", message: [%s]")
  //            + (TextUtils.isBlank(globallyPaidError) ? "" : ", API error: [%s]"),
  //        code,
  //        message,
  //        globallyPaidError);
  //  }
}

package com.deepstack.http;

import com.deepstack.exception.AuthenticationException;
import com.deepstack.exception.DeepStackException;
import com.deepstack.exception.InvalidRequestException;
import com.deepstack.model.Entity;
import com.deepstack.util.HmacUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpStatus;
import org.apache.http.util.TextUtils;

import static com.deepstack.util.Constants.*;

public abstract class BasicInterface extends Entity {
  public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
  public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

  /** The HTTP client. */
  private final Client client;

  /** The API version. */
  private static String version;

  /** The request headers container. */
  private final Map<String, String> requestHeaders = new HashMap<>();

  private static volatile String publishableApiKey;
  private static volatile String appId;
  private static volatile String sharedSecret;
  private static volatile String baseUrl = LIVE_BASE_URL;

  private static volatile int connectTimeout = -1;
  private static volatile int readTimeout = -1;

  private static volatile int maxNetworkRetries = 0;

  private static boolean sandbox;

  public BasicInterface(final Client client) {
    this.client = client;
  }

  public BasicInterface() {
    this.client = new Client();
  }

  public static void setSandbox(boolean sandbox) {
    BasicInterface.sandbox = sandbox;
    baseUrl = sandbox ? SANDBOX_BASE_URL : LIVE_BASE_URL;
  }

  /**
   * Initialize the client.
   *
   * @param config model {@link Config} with all configuration parameters.
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public static void initialize(Config config) throws AuthenticationException {
    if (TextUtils.isBlank(config.getPublishableApiKey())
        || TextUtils.isBlank(config.getAppId())
        || TextUtils.isBlank(config.getSharedSecret())) {
      throw new AuthenticationException("Api and App keys must be defined!");
    }

    publishableApiKey = "Bearer ".concat(config.getPublishableApiKey());
    appId = config.getAppId();
    sharedSecret = config.getSharedSecret();
    sandbox =
        !TextUtils.isBlank(config.getSandbox())
            && "true".equalsIgnoreCase(config.getSandbox().trim());
    baseUrl =
        !TextUtils.isBlank(config.getSandbox())
                && "true".equalsIgnoreCase(config.getSandbox().trim())
            ? SANDBOX_BASE_URL
            : LIVE_BASE_URL;
    version = API_VERSION;
  }

  public static void setVersion(String version) {
    BasicInterface.version = version;
  }

  /**
   * Returns the connection timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getConnectTimeout() {
    if (connectTimeout == -1) {
      return DEFAULT_CONNECT_TIMEOUT;
    }
    return connectTimeout;
  }

  /**
   * Sets the timeout value that will be used for making new connections to the GloballyPaid API (in
   * milliseconds).
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setConnectTimeout(final int timeout) {
    connectTimeout = timeout;
  }

  /**
   * Returns the read timeout.
   *
   * @return timeout value in milliseconds
   */
  public static int getReadTimeout() {
    if (readTimeout == -1) {
      return DEFAULT_READ_TIMEOUT;
    }
    return readTimeout;
  }

  /**
   * Sets the timeout value that will be used when reading data from an established connection to
   * the GloballyPaid API (in milliseconds).
   *
   * @param timeout timeout value in milliseconds
   */
  public static void setReadTimeout(final int timeout) {
    readTimeout = timeout;
  }

  /**
   * Returns the maximum number of times requests will be retried.
   *
   * @return the maximum number of times requests will be retried
   */
  public static int getMaxNetworkRetries() {
    return maxNetworkRetries;
  }

  /**
   * Sets the maximum number of times requests will be retried.
   *
   * @param maxNetworkRetries the maximum number of times requests will be retried
   */
  public static void setMaxNetworkRetries(int maxNetworkRetries) {
    BasicInterface.maxNetworkRetries = maxNetworkRetries;
  }

  /**
   * Initialize the client with authorization header.
   *
   * @param requestOptions the {@link RequestOptions} object to set per-request api key
   * @throws AuthenticationException In case of a bad defined API key
   */
  public void addAuthHeader(RequestOptions requestOptions) throws AuthenticationException {
    if (Objects.nonNull(requestOptions)
        && !TextUtils.isBlank(requestOptions.getPublishableApiKey())) {
      this.requestHeaders.put("Authorization", requestOptions.getPublishableApiKey());
    } else {
      if (TextUtils.isBlank(publishableApiKey)) {
        throw new AuthenticationException("Publishable Api key must be defined!");
      }

      this.requestHeaders.put("Authorization", publishableApiKey);
    }
  }

  /**
   * Initialize the client with HMAC header.
   *
   * @param content The request content
   * @param requestOptions The {@link RequestOptions} object
   * @throws InvalidRequestException In case of a problem during HMAC generation
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public void addHmacHeader(String content, RequestOptions requestOptions, String requestMethod)
      throws InvalidRequestException, AuthenticationException {
    if (Objects.nonNull(requestOptions)
        && !TextUtils.isBlank(requestOptions.getAppId())
        && !TextUtils.isBlank(requestOptions.getSharedSecret())) {
      String hmac =
          HmacUtils.createHmacHeader(
              content,
              requestOptions.getSharedSecret(),
              requestOptions.getAppId(),
              HMAC_ALGORITHM_TYPE,
                  requestMethod);
      this.requestHeaders.put(HMAC_HEADER, hmac);
    } else {
      if (TextUtils.isBlank(appId) || TextUtils.isBlank(sharedSecret)) {
        throw new AuthenticationException("Api and App keys must be defined!");
      }
      String hmac = HmacUtils.createHmacHeader(content, sharedSecret, appId, HMAC_ALGORITHM_TYPE, requestMethod);
      this.requestHeaders.put(HMAC_HEADER, hmac);
    }
  }

  public Map<String, String> getRequestHeaders() {
    return requestHeaders;
  }

  public void clearRequestHeaders(){
    requestHeaders.clear();
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Api sets up the request to the GloballyPaid API, this is main interface.
   *
   * @param request the (@link Request) object
   * @return the {@link Response} object
   * @throws DeepStackException in case of a network error
   */
  public Response api(final Request request) throws DeepStackException {
    final Request req = new Request();

    req.setMethod(request.getMethod());
    req.setBaseUri(request.getBaseUri());
    // This needs to be done in each request since ex: token uses a different base
    req.setEndpoint(API_BASE_URL.concat(version).concat(API_TRANSACTION_BASE).concat(request.getEndpoint()));
    req.setBody(request.getBody());
    req.setOptions(request.getOptions());
    req.setNonZeroCheck(request.isNonZeroCheck());

    if (!Objects.isNull(this.getRequestHeaders()) && !this.getRequestHeaders().isEmpty()) {
      for (final Map.Entry<String, String> header : this.requestHeaders.entrySet()) {
        req.addHeader(header.getKey(), header.getValue());
      }
    }

    if (!Objects.isNull(request.getQueryParams()) && !request.getQueryParams().isEmpty()) {
      for (final Map.Entry<String, String> queryParam : request.getQueryParams().entrySet()) {
        req.addQueryParam(queryParam.getKey(), queryParam.getValue());
      }
    }

    return makeApiCall(req);
  }

  public Response tokenapi(final Request request) throws DeepStackException {
    final Request req = new Request();

    req.setMethod(request.getMethod());
    req.setBaseUri(request.getBaseUri());
    // This needs to be done in each request since ex: token uses a different base
    req.setEndpoint(API_BASE_URL.concat(version).concat(API_TOKEN_BASE).concat(request.getEndpoint()));
    req.setBody(request.getBody());
    req.setOptions(request.getOptions());
    req.setNonZeroCheck(request.isNonZeroCheck());

    if (!Objects.isNull(this.getRequestHeaders()) && !this.getRequestHeaders().isEmpty()) {
      for (final Map.Entry<String, String> header : this.requestHeaders.entrySet()) {
        req.addHeader(header.getKey(), header.getValue());
      }
    }

    if (!Objects.isNull(request.getQueryParams()) && !request.getQueryParams().isEmpty()) {
      for (final Map.Entry<String, String> queryParam : request.getQueryParams().entrySet()) {
        req.addQueryParam(queryParam.getKey(), queryParam.getValue());
      }
    }

    return makeApiCall(req);
  }

  /**
   * Makes the call to the GloballyPaid API, override this method for testing.
   *
   * @param request the {@link Request} to make
   * @return the {@link Response} object
   * @throws DeepStackException in case of a network error
   */
  public Response makeApiCall(final Request request) throws DeepStackException {
    return client.api(request);
  }

  /**
   * URL-encode a string ID in url path formatting.
   *
   * @param id id
   * @return Url encoded string from id
   * @throws InvalidRequestException In case of an invalid Id
   */
  public static String urlEncodeId(String id) throws InvalidRequestException {
    if (id == null) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST,
          "Invalid null ID found for url path formatting. This can be because your string ID "
              + "argument to the API method is null, or the ID field in your object instance is null.",
          null,
          null);
    }

    try {
      return URLEncoder.encode(id, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST,
          "The character encoding UTF-8 is unknown while encoding ID for url path formatting!",
          null,
          null);
    }
  }
}

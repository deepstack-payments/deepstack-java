package com.deepstack.http;

import com.deepstack.exception.*;
import com.deepstack.exception.DeepStackException;
import com.deepstack.util.JsonUtils;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.TextUtils;

import static com.deepstack.util.Constants.MESSAGE;
import static com.deepstack.util.Constants.RESPONSE_CODE;
import static com.deepstack.util.Constants.ZERO_CODE;
import static java.lang.Math.pow;

/** A Client for easy access to GloballyPaid's API. */
public class Client {

  /** Maximum sleep time between tries to send HTTP requests after network failure. */
  public static final Duration maxNetworkRetriesDelay = Duration.ofSeconds(5);

  /** Minimum sleep time between tries to send HTTP requests after network failure. */
  public static final Duration minNetworkRetriesDelay = Duration.ofMillis(500);

  /**
   * Build URI with query parameters.
   *
   * @param baseUri e.g. "transactions.globallypaid.com"
   * @param endpoint e.g. "/your/endpoint/path"
   * @param queryParams Map of of key/values representing the query parameters
   * @return URI
   * @throws URISyntaxException In case of a URI syntax error
   */
  public URI buildUri(String baseUri, String endpoint, Map<String, String> queryParams)
      throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder();
    uriBuilder.setScheme("https");
    uriBuilder.setHost(baseUri);
    uriBuilder.setPath(endpoint);

    if (queryParams != null) {
      for (final Map.Entry<String, String> param : queryParams.entrySet()) {
        uriBuilder.setParameter(param.getKey(), param.getValue());
      }
    }

    return uriBuilder.build();
  }

  /**
   * Make a GET request.
   *
   * @param request the {@link Request} object
   * @return the {@link Response} object
   * @throws URISyntaxException In case of a URI syntax error
   * @throws DeepStackException In case of an API error
   */
  public Response get(Request request) throws URISyntaxException, DeepStackException {
    URI uri = this.buildUri(request.getBaseUri(), request.getEndpoint(), request.getQueryParams());
    HttpGet httpGet = new HttpGet(uri.toString());

    if (request.getHeaders() != null) {
      for (final Map.Entry<String, String> header : request.getHeaders().entrySet()) {
        httpGet.addHeader(header.getKey(), header.getValue());
      }
    }

    return this.executeApiCallWithRetries(httpGet, request.getOptions());
  }

  /**
   * Make a POST request.
   *
   * @param request the {@link Request} object
   * @return the {@link Response} object
   * @throws URISyntaxException In case of a URI syntax error
   * @throws DeepStackException In case of an API error
   */
  public Response post(Request request) throws URISyntaxException, DeepStackException {
    URI uri = this.buildUri(request.getBaseUri(), request.getEndpoint(), request.getQueryParams());
    HttpPost httpPost = new HttpPost(uri.toString());

    if (request.getHeaders() != null) {
      for (final Map.Entry<String, String> header : request.getHeaders().entrySet()) {
        httpPost.addHeader(header.getKey(), header.getValue());
      }
    }

    httpPost.setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
    writeContentTypeIfNeeded(request, httpPost);
    Response postResponse = this.executeApiCallWithRetries(httpPost, request.getOptions());

    if (request.isNonZeroCheck()
        && postResponse.getStatusCode() == 200
        && !TextUtils.isBlank(postResponse.getBody())) {
      handleNonZeroResponseCode(postResponse);
    }
    return postResponse;
  }

  /**
   * Make a PUT request.
   *
   * @param request the {@link Request} object
   * @return the {@link Response} object
   * @throws URISyntaxException In case of a URI syntax error
   * @throws DeepStackException In case of an API error
   */
  public Response put(Request request) throws URISyntaxException, DeepStackException {
    URI uri = this.buildUri(request.getBaseUri(), request.getEndpoint(), request.getQueryParams());
    HttpPut httpPut = new HttpPut(uri.toString());

    if (request.getHeaders() != null) {
      for (final Map.Entry<String, String> header : request.getHeaders().entrySet()) {
        httpPut.addHeader(header.getKey(), header.getValue());
      }
    }

    httpPut.setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
    this.writeContentTypeIfNeeded(request, httpPut);
    return this.executeApiCallWithRetries(httpPut, request.getOptions());
  }

  /**
   * Make a DELETE request.
   *
   * @param request the {@link Request} object
   * @return the {@link Response} object
   * @throws URISyntaxException In case of a URI syntax error
   * @throws DeepStackException In case of an API error
   */
  public Response delete(Request request) throws URISyntaxException, DeepStackException {
    URI uri = this.buildUri(request.getBaseUri(), request.getEndpoint(), request.getQueryParams());
    HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(uri.toString());

    if (request.getHeaders() != null) {
      for (final Map.Entry<String, String> header : request.getHeaders().entrySet()) {
        httpDelete.addHeader(header.getKey(), header.getValue());
      }
    }

    httpDelete.setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
    this.writeContentTypeIfNeeded(request, httpDelete);
    return this.executeApiCallWithRetries(httpDelete, request.getOptions());
  }

  private void writeContentTypeIfNeeded(Request request, HttpRequestBase requestBase) {
    if (!"".equals(request.getBody())) {
      requestBase.setHeader("Content-Type", "application/json");
    }
  }

  /**
   * Sends the given request to GloballyPaid's API, retrying the request in cases of intermittent
   * problems.
   *
   * @param requestBase the {@link HttpRequestBase} object
   * @param requestOptions the {@link RequestOptions} object
   * @return the {@link Response} object
   * @throws DeepStackException If the request fails for any reason
   */
  private Response executeApiCallWithRetries(
      HttpRequestBase requestBase, RequestOptions requestOptions) throws DeepStackException {
    DeepStackException requestException;
    Response response = null;
    int retry = 0;

    while (true) {
      requestException = null;

      try {
        response = executeApiCall(requestBase, requestOptions);
      } catch (DeepStackException e) {
        requestException = e;
      }

      if (!this.shouldRetry(retry, requestException, requestOptions, response)) {
        break;
      }

      retry += 1;

      try {
        Thread.sleep(this.sleepTime(retry).toMillis());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    if (requestException != null) {
      throw requestException;
    }

    response.setNumRetries(retry);

    return response;
  }

  /**
   * Sends the given request to GloballyPaid's API.
   *
   * @param requestBase the {@link HttpRequestBase} object
   * @param requestOptions the {@link RequestOptions} object
   * @return the {@link Response} object
   * @throws DeepStackException If the request fails for any reason
   */
  private Response executeApiCall(HttpRequestBase requestBase, RequestOptions requestOptions)
      throws DeepStackException {
    try {
      CloseableHttpClient httpClient =
          HttpClientBuilder.create()
              .setDefaultRequestConfig(getRequestConfig(requestOptions))
              .build();
      CloseableHttpResponse apiResponse = httpClient.execute(requestBase);
      Response response;
      try {
        response = this.getResponse(apiResponse);
      } finally {
        apiResponse.close();
      }
      return response;
    } catch (IOException e) {
      throw new ApiConnectionException(
          String.format(
              "IOException during request to GloballyPaid API (%s): %s "
                  + "Please check your internet connection and try again. If this problem persists,"
                  + "you should check GloballyPaid's service status at .....,"
                  + " or let us know at support@globallypaid.com.",
              requestBase.getURI().toString(), e.getMessage()),
          e);
    }
  }

  private RequestConfig getRequestConfig(RequestOptions requestOptions) {
    return RequestConfig.custom()
        .setConnectionRequestTimeout(requestOptions.getConnectTimeout())
        .setConnectTimeout(requestOptions.getConnectTimeout())
        .setSocketTimeout(requestOptions.getReadTimeout())
        .build();
  }

  /**
   * A wrapper around the HTTP methods.
   *
   * @param request The {@link Request} object
   * @return The {@link Response} object
   * @throws DeepStackException If the request fails for any reason
   */
  public Response api(Request request) throws DeepStackException {
    try {
      if (request.getMethod() == null) {
        throw new InvalidRequestException(
            404, "We only support GET, PUT, PATCH, POST and DELETE methods!", null, null);
      } else {
        switch (request.getMethod()) {
          case GET:
            return this.get(request);
          case POST:
            return this.post(request);
          case PUT:
            return this.put(request);
            //          case PATCH:
            //            return this.patch(request);
          case DELETE:
            return this.delete(request);
          default:
            throw new InvalidRequestException(
                404, "We only support GET, PUT, PATCH, POST and DELETE methods!", null, null);
        }
      }
    } catch (URISyntaxException ex) {
      throw new ApiConnectionException(
          String.format(
              "URI Exception during request (%s): %s!",
              request.getBaseUri().concat(request.getEndpoint()), ex.getMessage()),
          ex);
    }
  }

  /**
   * Prepare a Response object from an GloballyPaid API call via Apache's HTTP client.
   *
   * @param response from a call to a CloseableHttpClient
   * @return the {@link Response} object
   * @throws DeepStackException If the request fails for any reason
   */
  public Response getResponse(CloseableHttpResponse response) throws DeepStackException {
    ResponseHandler<String> handler = new DeepStackResponseHandler();
    int statusCode = response.getStatusLine().getStatusCode();
    Header[] headers = response.getAllHeaders();
    Map<String, String> responseHeaders = new HashMap<>();
    Header[] resHeaders = headers;
    int headersLength = headers.length;

    for (int i = 0; i < headersLength; ++i) {
      Header h = resHeaders[i];
      responseHeaders.put(h.getName(), h.getValue());
    }

    String responseBody;
    try {
      responseBody = handler.handleResponse(response);
    } catch (IOException e) {
      throw new ApiException(
          statusCode,
          String.format(
              "IOException during reading the GloballyPaid API response %s.", e.getMessage()),
          e);
    }

    Response res =
        Response.builder()
            .statusCode(statusCode)
            .body(responseBody)
            .headers(responseHeaders)
            .build();

    if (statusCode < 200 || statusCode >= 300) {
      handleApiError(res);
    }

    return res;
  }

  private void handleApiError(Response response) throws DeepStackException {
    DeepStackException exception;

    Integer statusCode = response.getStatusCode();
    switch (statusCode) {
      case 400:
        exception =
            new InvalidRequestException(
                statusCode, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
        break;
      case 404:
        exception =
            new InvalidRequestException(statusCode, ErrorMessage.NOT_FOUND.getLabel(), null, null);
        break;
      case 401:
        exception = new AuthenticationException(statusCode, ErrorMessage.UNAUTHORIZED.getLabel());
        break;
      case 403:
        exception = new ForbiddenException(statusCode, ErrorMessage.FORBIDDEN.getLabel(), null);
        break;
      case 405:
        exception = new NotAllowedException(statusCode, ErrorMessage.METHOD_NOT_ALLOWED.getLabel());
        break;
      case 406:
        exception = new NotAcceptableException(statusCode, ErrorMessage.NOT_ACCEPTABLE.getLabel());
        break;
      case 410:
        exception = new ApiException(statusCode, ErrorMessage.GONE.getLabel(), null);
        break;
      case 429:
        exception =
            new RateLimitException(statusCode, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null);
        break;
      case 503:
        exception = new ApiException(statusCode, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null);
        break;
      default:
        exception =
            new ApiException(statusCode, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null);
        break;
    }

    try {
      exception.setDeepStackError(response.getBody());
    } catch (IOException e) {
      throw new ApiException(
          statusCode,
          String.format(
              "Invalid response object from API: %s. (HTTP response code was %d)",
              response.getBody(), statusCode),
          e);
    }
    throw exception;
  }

  private void handleNonZeroResponseCode(Response res) throws InvalidRequestException {
    try {
      Map<String, Object> resBody = JsonUtils.convertFromJsonToObject(res.getBody(), Map.class);
      Object resCode = resBody.get(RESPONSE_CODE);
      if (!Objects.isNull(resCode) && !ZERO_CODE.equals(resCode)) {
        InvalidRequestException exception =
            new InvalidRequestException(
                HttpStatus.SC_BAD_REQUEST,
                Objects.isNull(resBody.get(MESSAGE))
                    ? "No message provided from API!"
                    : resBody.get(MESSAGE).toString(),
                null,
                null);
        exception.setDeepStackError(res.getBody());
        throw exception;
      }
    } catch (IOException e) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST.getLabel(), null, e);
    }
  }

  private boolean shouldRetry(
      int numRetries,
      DeepStackException exception,
      RequestOptions requestOptions,
      Response response) {

    if (numRetries >= requestOptions.getMaxNetworkRetries()) {
      return false;
    }

    // Retry on connection error.
    if ((exception != null)
        && (exception.getCause() != null)
        && (exception.getCause() instanceof ConnectException
            || exception.getCause() instanceof SocketTimeoutException
            || exception instanceof ApiConnectionException
            || exception instanceof ApiException)) {
      return true;
    }

    // Retry on conflict errors.
    if ((response != null) && (response.getStatusCode() == 409)) {
      return true;
    }

    // Retry on 500, 503, and other internal errors.
    if ((response != null) && (response.getStatusCode() >= 500)) {
      return true;
    }

    return false;
  }

  private Duration sleepTime(int numRetries) {
    // Apply exponential backoff with MinNetworkRetriesDelay on the number of numRetries
    // so far as inputs.
    Duration delay =
        Duration.ofNanos((long) (minNetworkRetriesDelay.toNanos() * pow(2, numRetries - 1f)));

    // Do not allow the number to exceed MaxNetworkRetriesDelay
    if (delay.compareTo(maxNetworkRetriesDelay) > 0) {
      delay = maxNetworkRetriesDelay;
    }

    // Apply some jitter by randomizing the value in the range of 75%-100%.
    double jitter = ThreadLocalRandom.current().nextDouble(0.75, 1.0);
    delay = Duration.ofNanos((long) (delay.toNanos() * jitter));

    // But never sleep less than the base sleep seconds.
    if (delay.compareTo(minNetworkRetriesDelay) < 0) {
      delay = minNetworkRetriesDelay;
    }

    return delay;
  }
}

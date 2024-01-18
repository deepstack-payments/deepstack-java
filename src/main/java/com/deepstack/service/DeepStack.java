package com.deepstack.service;

import com.deepstack.exception.DeepStackException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.deepstack.exception.AuthenticationException;
import com.deepstack.exception.InvalidRequestException;
import com.deepstack.http.BasicInterface;
import com.deepstack.http.Config;
import com.deepstack.http.ErrorMessage;
import com.deepstack.http.Method;
import com.deepstack.http.Request;
import com.deepstack.http.RequestOptions;
import com.deepstack.http.Response;
import com.deepstack.model.*;
import com.deepstack.model.PaymentInstrumentToken;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepStack extends BasicInterface {

  /**
   * Initialize the client.
   *
   * @param config model {@link Config} with all configuration parameters
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public DeepStack(Config config) throws AuthenticationException {
    initialize(config);
  }

  /**
   * To tokenize the card data. Typically, this step is performed using our JS SDK.
   *
   * @param tokenRequest The {@link TokenRequest} object
   * @return The new {@link PaymentInstrumentToken} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public PaymentInstrumentToken token(TokenRequest tokenRequest)
      throws IOException, DeepStackException {
    return token(tokenRequest, null);
  }

  /**
   * To tokenize the card data with request options. Typically, this step is performed using our JS
   * SDK.
   *
   * @param tokenRequest The {@link TokenRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return The new {@link PaymentInstrumentToken} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public PaymentInstrumentToken token(TokenRequest tokenRequest, RequestOptions requestOptions)
      throws IOException, DeepStackException {
    this.addAuthHeader(requestOptions);
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/token")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(tokenRequest.toJson())
            .options(requestOptions)
            .build();

    Response response = this.tokenapi(request);
    // Need to clear headers after using them
    this.clearRequestHeaders();
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return PaymentInstrumentToken.builder()
        .build()
        .fromJson(response.getBody(), PaymentInstrumentToken.class);
  }

  /**
   * Create a payment instrument using a token. Typically can just run a charge with a token with 'save_payment_instrument':true
   * @param paymentInstrumentRequest
   * @return
   * @throws IOException
   * @throws DeepStackException
   */
  public PaymentInstrumentToken createPaymentInstrument(PaymentInstrumentRequest paymentInstrumentRequest)
          throws IOException, DeepStackException {
    return createPaymentInstrument(paymentInstrumentRequest, null);
  }

  /**
   * Create a payment instrument using a token. Typically can just run a charge with a token with 'save_payment_instrument':true
   * @param paymentInstrumentRequest
   * @param requestOptions
   * @return
   * @throws IOException
   * @throws DeepStackException
   */
  public PaymentInstrumentToken createPaymentInstrument(PaymentInstrumentRequest paymentInstrumentRequest, RequestOptions requestOptions)
          throws IOException, DeepStackException {
    this.addHmacHeader(paymentInstrumentRequest.toJson(), requestOptions, "POST");
    Request request =
            Request.builder()
                    .baseUri(getBaseUrl())
                    .endpoint("/payment-instrument/token")
                    .method(Method.POST)
                    .headers(getRequestHeaders())
                    .body(paymentInstrumentRequest.toJson())
                    .options(requestOptions)
                    .build();
    Response response = this.tokenapi(request);
    this.clearRequestHeaders();

    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return PaymentInstrumentToken.builder()
            .build()
            .fromJson(response.getBody(), PaymentInstrumentToken.class);

  }

  /**
   * Delete payment instrument by paymentInstrumentID
   * @param id
   * @return
   * @throws DeepStackException
   */
  public boolean delete(String id)throws DeepStackException {
    return this.delete(id, null);
  }

  /**
   * Delete payment instrument by paymentInstrumentID
   * @param id
   * @param requestOptions
   * @return
   * @throws DeepStackException
   */
  public boolean delete(String id, RequestOptions requestOptions) throws DeepStackException {
    this.addHmacHeader("", requestOptions, "DELETE");
    String endpoint = URI.create("/payment-instrument/delete/".concat(urlEncodeId(id))).toString();
    Request request =
            Request.builder()
                    .baseUri(getBaseUrl())
                    .endpoint(endpoint)
                    .method(Method.DELETE)
                    .headers(getRequestHeaders())
                    .body("")
                    .options(requestOptions)
                    .build();

    Response response = this.tokenapi(request);
    return response.getBody().equals("true");
  }


  /**
   * To charge a credit card or other payment source.
   *
   * @param chargeRequest The {@link ChargeRequest} object
   * @return The new {@link ChargeResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public ChargeResponse charge(ChargeRequest chargeRequest)
      throws IOException, DeepStackException {
    return charge(chargeRequest, null);
  }

  /**
   * To charge a credit card or other payment source with request options.
   *
   * @param chargeRequest The {@link ChargeRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return The new {@link ChargeResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public ChargeResponse charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
      throws IOException, DeepStackException {
    if (!Optional.ofNullable(chargeRequest).isPresent()
        || Objects.isNull(chargeRequest.getSource())
        || chargeRequest.getSource() == null) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST, "You must provide charge source!", null, null);
    }

    this.addHmacHeader(chargeRequest.toJson(), requestOptions, "POST");
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/charge")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(chargeRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    this.clearRequestHeaders();
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return ChargeResponse.builder().build().fromJson(response.getBody(), ChargeResponse.class);
  }

  /**
   * Capture the payment of an existing uncaptured charge. This is the second part of the two-step
   * payment flow, where first you create a charge with the capture option set to false.
   *
   * @param captureRequest The {@link CaptureRequest} object
   * @return the new {@link CaptureResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   * @see #charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
   */
  public CaptureResponse capture(CaptureRequest captureRequest)
      throws IOException, DeepStackException {
    return capture(captureRequest, null);
  }

  /**
   * Capture the payment of an existing uncaptured charge with request options. This is the second
   * part of the two-step payment flow, where first you create a charge with the capture option set
   * to false.
   *
   * @param captureRequest The {@link CaptureRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return the new {@link CaptureResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   * @see #charge(ChargeRequest chargeRequest, RequestOptions requestOptions)
   */
  public CaptureResponse capture(CaptureRequest captureRequest, RequestOptions requestOptions)
      throws IOException, DeepStackException {
    this.addHmacHeader(captureRequest.toJson(), requestOptions, "POST");
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/capture")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(captureRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    this.clearRequestHeaders();
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return CaptureResponse.builder().build().fromJson(response.getBody(), CaptureResponse.class);
  }

  /**
   * To refund a payment, you create a <code>RefundRequest</code> with <code>Charge</code>
   * identifier. You can also optionally refund part of a charge by specifying an amount.
   *
   * @param refundRequest The {@link RefundRequest} object
   * @return the new {@link RefundResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public RefundResponse refund(RefundRequest refundRequest)
      throws IOException, DeepStackException {
    return refund(refundRequest, null);
  }

  /**
   * To refund a payment, you create a <code>RefundRequest</code> with <code>Charge</code>
   * identifier. You can also optionally refund part of a charge by specifying an amount.
   *
   * @param refundRequest The {@link RefundRequest} object
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return the new {@link RefundResponse} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public RefundResponse refund(RefundRequest refundRequest, RequestOptions requestOptions)
      throws IOException, DeepStackException {
    this.addHmacHeader(refundRequest.toJson(), requestOptions, "POST");
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint("/refund")
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(refundRequest.toJson())
            .options(requestOptions)
            .nonZeroCheck(true)
            .build();

    Response response = this.api(request);
    this.clearRequestHeaders();
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return RefundResponse.builder().build().fromJson(response.getBody(), RefundResponse.class);
  }
}

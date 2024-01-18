package functional;

import com.deepstack.exception.*;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.ErrorMessage;
import com.deepstack.http.Response;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.TokenRequest;
import com.deepstack.service.Customer;
import com.deepstack.service.PaymentInstrument;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import model.CommonMockModel;
import model.CustomerMockModel;
import model.GloballyPaidMockModel;
import model.PaymentInstrumentMockModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentInstrumentTest {
  @Mock PaymentInstrument paymentInstrument;

  @Test
  public void testCreatePaymentInstrumentSuccess() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    Response expectedResponse =
        CommonMockModel.response200WithCode00(expectedPaymentInstrumentToken);

    lenient().when(paymentInstrument.api(any())).thenReturn(expectedResponse);
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    PaymentInstrumentToken actualResponse = paymentInstrument.create();

    assertTrue(Objects.nonNull(actualResponse));
    assertTrue(Objects.nonNull(actualResponse.getId()));
    assertEquals(expectedPaymentInstrumentToken.getType(), actualResponse.getType());
    assertEquals(expectedPaymentInstrumentToken.getExpiration(), actualResponse.getExpiration());
    assertEquals(expectedPaymentInstrumentToken.getLastFour(), actualResponse.getLastFour());
    assertEquals(expectedPaymentInstrumentToken.getCustomerId(), actualResponse.getCustomerId());
  }

  @Test
  public void testCreatePaymentInstrumentWithNullResponseBodyReturnExecption()
      throws DeepStackException, IOException {
    lenient().when(paymentInstrument.api(any())).thenReturn(null);
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> paymentInstrument.create());

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith400() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, paymentInstrument::create);

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith404() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, paymentInstrument::create);

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith401() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(AuthenticationException.class, paymentInstrument::create);

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith403() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ForbiddenException.class, paymentInstrument::create);

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith405() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAllowedException.class, paymentInstrument::create);

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith406() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAcceptableException.class, paymentInstrument::create);

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith410() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, paymentInstrument::create);

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith429() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(RateLimitException.class, paymentInstrument::create);

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith503() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, paymentInstrument::create);

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreatePaymentInstrumentFailWith500() throws DeepStackException, IOException {
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(paymentInstrument.create()).thenCallRealMethod();
    lenient().when(paymentInstrument.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, paymentInstrument::create);

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentSuccess() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    Response expectedResponse =
        CommonMockModel.response200WithCode00(expectedPaymentInstrumentToken);

    lenient().when(paymentInstrument.api(any())).thenReturn(expectedResponse);
    lenient().when(paymentInstrument.update(any())).thenCallRealMethod();
    lenient().when(paymentInstrument.update(any(), any())).thenCallRealMethod();

    PaymentInstrumentToken actualResponse =
        paymentInstrument.update(expectedPaymentInstrumentToken.getId());

    assertTrue(Objects.nonNull(actualResponse));
    assertTrue(Objects.nonNull(actualResponse.getId()));
    assertEquals(expectedPaymentInstrumentToken.getType(), actualResponse.getType());
    assertEquals(expectedPaymentInstrumentToken.getExpiration(), actualResponse.getExpiration());
    assertEquals(expectedPaymentInstrumentToken.getLastFour(), actualResponse.getLastFour());
    assertEquals(expectedPaymentInstrumentToken.getCustomerId(), actualResponse.getCustomerId());
  }

  @Test
  public void testUpdatePaymentInstrumentWithNullIdFail()
      throws IOException, DeepStackException {
    when(paymentInstrument.update(null)).thenCallRealMethod();
    when(paymentInstrument.update(null, null)).thenCallRealMethod();
    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> paymentInstrument.update(null));

    assertEquals(400, exception.getCode());
    assertTrue(exception.getMessage().contains("Invalid null ID"));
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith400() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith401() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            AuthenticationException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith403() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ForbiddenException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith404() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith405() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            NotAllowedException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith406() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            NotAcceptableException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith410() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith429() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            RateLimitException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith503() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdatePaymentInstrumentFailWith500() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.update(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.update(expectedPaymentInstrumentToken.getId()));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentSuccess() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    Response expectedResponse =
        CommonMockModel.response200WithCode00(expectedPaymentInstrumentToken);

    lenient().when(paymentInstrument.api(any())).thenReturn(expectedResponse);
    lenient().when(paymentInstrument.retrieve(any())).thenCallRealMethod();
    lenient().when(paymentInstrument.retrieve(any(), any())).thenCallRealMethod();

    PaymentInstrumentToken actualResponse =
        paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId());

    assertTrue(Objects.nonNull(actualResponse));
    assertEquals(expectedPaymentInstrumentToken.getId(), actualResponse.getId());
    assertEquals(expectedPaymentInstrumentToken.getType(), actualResponse.getType());
    assertEquals(expectedPaymentInstrumentToken.getExpiration(), actualResponse.getExpiration());
    assertEquals(expectedPaymentInstrumentToken.getLastFour(), actualResponse.getLastFour());
    assertEquals(expectedPaymentInstrumentToken.getCustomerId(), actualResponse.getCustomerId());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith400() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith401() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            AuthenticationException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith403() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ForbiddenException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith404() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith405() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            NotAllowedException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith406() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            NotAcceptableException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith410() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith429() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            RateLimitException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith503() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrievePaymentInstrumentFailWith500() throws DeepStackException, IOException {
    TokenRequest tokenRequest = GloballyPaidMockModel.tokenRequest();
    PaymentInstrumentToken expectedPaymentInstrumentToken =
        GloballyPaidMockModel.paymentInstrumentToken(tokenRequest);
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()))
        .thenCallRealMethod();
    lenient()
        .when(paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId(), null))
        .thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            ApiException.class,
            () -> paymentInstrument.retrieve(expectedPaymentInstrumentToken.getId()));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentSuccess() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    List<PaymentInstrumentToken> expectedPaymentInstruments =
        PaymentInstrumentMockModel.paymentInstrumentList(customer);
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedPaymentInstruments);

    lenient().when(paymentInstrument.api(any())).thenReturn(expectedResponse);
    lenient().when(paymentInstrument.list(any())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(any(), any(), any())).thenCallRealMethod();

    List<PaymentInstrumentToken> actualResponse = paymentInstrument.list(customer.getId());

    assertTrue(Objects.nonNull(actualResponse));
    assertFalse(actualResponse.isEmpty());
    assertEquals(expectedPaymentInstruments.size(), actualResponse.size());
  }

  @Test
  public void testListPaymentInstrumentFailWith400() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith401() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(AuthenticationException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith403() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ForbiddenException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith404() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith405() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAllowedException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith406() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAcceptableException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith410() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith429() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(RateLimitException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith503() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListPaymentInstrumentFailWith500() throws DeepStackException, IOException {
    Customer customer = CustomerMockModel.customer();
    lenient()
        .when(paymentInstrument.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(paymentInstrument.list(customer.getId())).thenCallRealMethod();
    lenient().when(paymentInstrument.list(customer.getId(), null, null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> paymentInstrument.list(customer.getId()));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }
}

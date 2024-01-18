package functional;

import com.deepstack.exception.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.ErrorMessage;
import com.deepstack.http.Response;
import com.deepstack.service.Customer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.CommonMockModel;
import model.CustomerMockModel;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {
  @Mock Customer customer;

  @Test
  public void testCreateCustomerSuccess() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();

    Response expectedResponse = CommonMockModel.response200WithCode00(expectedCustomer);

    lenient().when(customer.api(any())).thenReturn(expectedResponse);
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    Customer actualResponse = customer.create();

    assertTrue(Objects.nonNull(actualResponse));
    assertTrue(Objects.nonNull(actualResponse.getId()));
    assertEquals(expectedCustomer.getFirstName(), actualResponse.getFirstName());
  }

  @Test
  public void testCreateCustomerWithoutClientCustomerIdFail()
      throws DeepStackException, IOException {
    DeepStackException gpe =
        new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    gpe.setDeepStackError(CustomerMockModel.customerCreateGloballyPaidError());

    Customer expectedCustomerWithoutClientCustomerId = mock(Customer.class);
    lenient().when(expectedCustomerWithoutClientCustomerId.api(any())).thenThrow(gpe);
    lenient().when(expectedCustomerWithoutClientCustomerId.create()).thenCallRealMethod();
    lenient().when(expectedCustomerWithoutClientCustomerId.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class, expectedCustomerWithoutClientCustomerId::create);

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
    assertTrue(Objects.nonNull(exception.getDeepStackError()));
    Object errors = exception.getDeepStackError().get("errors");
    assertTrue(Objects.nonNull(errors));
    Map<String, Object> error =
        new ObjectMapper().convertValue(errors, new TypeReference<Map<String, Object>>() {});
    assertTrue(Objects.nonNull(error.get("client_customer_id")));
    List<String> errorList =
        new ObjectMapper()
            .convertValue(error.get("client_customer_id"), new TypeReference<List<String>>() {});
    assertFalse(errorList.isEmpty());
    assertTrue(errorList.contains("The input was not valid."));
  }

  @Test
  public void testCreateCustomerWithNullResponseBodyReturnExecption()
      throws DeepStackException, IOException {
    lenient().when(customer.api(any())).thenReturn(null);
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> customer.create());

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith400() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(InvalidRequestException.class, customer::create);

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith404() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(InvalidRequestException.class, customer::create);

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith401() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(AuthenticationException.class, customer::create);

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith403() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ForbiddenException.class, customer::create);

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith405() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(NotAllowedException.class, customer::create);

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith406() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(NotAcceptableException.class, customer::create);

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith410() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::create);

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith429() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(RateLimitException.class, customer::create);

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith503() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::list);

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith500() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::list);

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerSuccess() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedCustomer);

    lenient().when(customer.api(any())).thenReturn(expectedResponse);
    lenient().when(customer.update(any())).thenCallRealMethod();
    lenient().when(customer.update(any(), any())).thenCallRealMethod();

    Customer actualResponse = customer.update(expectedCustomer.getId());

    assertTrue(Objects.nonNull(actualResponse));
    assertTrue(Objects.nonNull(actualResponse.getId()));
    assertEquals(expectedCustomer.getFirstName(), actualResponse.getFirstName());
  }

  @Test
  public void testUpdateCustomerFailWith400() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith401() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            AuthenticationException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith403() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ForbiddenException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith404() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith405() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAllowedException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith406() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAcceptableException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith410() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith429() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(RateLimitException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith503() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerFailWith500() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(customer.update(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.update(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.update(expectedCustomer.getId()));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerSuccess() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedCustomer);

    lenient().when(customer.api(any())).thenReturn(expectedResponse);
    lenient().when(customer.retrieve(any())).thenCallRealMethod();
    lenient().when(customer.retrieve(any(), any())).thenCallRealMethod();

    Customer actualResponse = customer.retrieve(expectedCustomer.getId());

    assertTrue(Objects.nonNull(actualResponse));
    assertEquals(expectedCustomer.getId(), actualResponse.getId());
    assertEquals(expectedCustomer.getFirstName(), actualResponse.getFirstName());
  }

  @Test
  public void testRetrieveCustomerFailWith400() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith401() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            AuthenticationException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith403() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ForbiddenException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith404() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            InvalidRequestException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith405() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(NotAllowedException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith406() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(
            NotAcceptableException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith410() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith429() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(RateLimitException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith503() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testRetrieveCustomerFailWith500() throws DeepStackException, IOException {
    Customer expectedCustomer = CustomerMockModel.customer();
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(customer.retrieve(expectedCustomer.getId())).thenCallRealMethod();
    lenient().when(customer.retrieve(expectedCustomer.getId(), null)).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(ApiException.class, () -> customer.retrieve(expectedCustomer.getId()));

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerSuccess() throws DeepStackException, IOException {
    List<Customer> expectedCustomers = CustomerMockModel.customerList();
    Response expectedResponse = CommonMockModel.response200WithCode00(expectedCustomers);

    lenient().when(customer.api(any())).thenReturn(expectedResponse);
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    List<Customer> actualResponse = customer.list();

    assertTrue(Objects.nonNull(actualResponse));
    assertFalse(actualResponse.isEmpty());
    assertEquals(expectedCustomers.size(), actualResponse.size());
  }

  @Test
  public void testListCustomerFailWith400() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(
            new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> customer.list());

    assertEquals(400, exception.getCode());
    assertEquals(ErrorMessage.BAD_REQUEST.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith401() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new AuthenticationException(401, ErrorMessage.UNAUTHORIZED.getLabel()));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception =
        assertThrows(AuthenticationException.class, () -> customer.list());

    assertEquals(401, exception.getCode());
    assertEquals(ErrorMessage.UNAUTHORIZED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith403() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ForbiddenException(403, ErrorMessage.FORBIDDEN.getLabel(), null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ForbiddenException.class, customer::list);

    assertEquals(403, exception.getCode());
    assertEquals(ErrorMessage.FORBIDDEN.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith405() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAllowedException(405, ErrorMessage.METHOD_NOT_ALLOWED.getLabel()));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(NotAllowedException.class, customer::list);

    assertEquals(405, exception.getCode());
    assertEquals(ErrorMessage.METHOD_NOT_ALLOWED.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith404() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new InvalidRequestException(404, ErrorMessage.NOT_FOUND.getLabel(), null, null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(InvalidRequestException.class, customer::list);

    assertEquals(404, exception.getCode());
    assertEquals(ErrorMessage.NOT_FOUND.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith406() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new NotAcceptableException(406, ErrorMessage.NOT_ACCEPTABLE.getLabel()));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(NotAcceptableException.class, customer::list);

    assertEquals(406, exception.getCode());
    assertEquals(ErrorMessage.NOT_ACCEPTABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith410() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(410, ErrorMessage.GONE.getLabel(), null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::list);

    assertEquals(410, exception.getCode());
    assertEquals(ErrorMessage.GONE.getLabel(), exception.getMessage());
  }

  @Test
  public void testListCustomerFailWith429() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new RateLimitException(429, ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), null));
    lenient().when(customer.list()).thenCallRealMethod();
    lenient().when(customer.list(any(), any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(RateLimitException.class, customer::list);

    assertEquals(429, exception.getCode());
    assertEquals(ErrorMessage.RATE_LIMIT_EXCEEDED.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith503() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(503, ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::create);

    assertEquals(503, exception.getCode());
    assertEquals(ErrorMessage.SERVICE_UNAVAILABLE.getLabel(), exception.getMessage());
  }

  @Test
  public void testCreateCustomerFailWith500() throws DeepStackException, IOException {
    lenient()
        .when(customer.api(any()))
        .thenThrow(new ApiException(500, ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), null));
    lenient().when(customer.create()).thenCallRealMethod();
    lenient().when(customer.create(any())).thenCallRealMethod();

    DeepStackException exception = assertThrows(ApiException.class, customer::create);

    assertEquals(500, exception.getCode());
    assertEquals(ErrorMessage.INTERNAL_SERVER_ERROR.getLabel(), exception.getMessage());
  }

  @Test
  public void testUpdateCustomerWithNullIdFail() throws IOException, DeepStackException {
    when(customer.update(null)).thenCallRealMethod();
    when(customer.update(null, null)).thenCallRealMethod();
    DeepStackException exception =
        assertThrows(InvalidRequestException.class, () -> customer.update(null));

    assertEquals(400, exception.getCode());
    assertTrue(exception.getMessage().contains("Invalid null ID"));
  }
}

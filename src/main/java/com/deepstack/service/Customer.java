package com.deepstack.service;

import com.deepstack.exception.DeepStackException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.deepstack.exception.AuthenticationException;
import com.deepstack.exception.InvalidRequestException;
import com.deepstack.http.BasicInterface;
import com.deepstack.http.Config;
import com.deepstack.http.ErrorMessage;
import com.deepstack.http.Method;
import com.deepstack.http.Request;
import com.deepstack.http.RequestOptions;
import com.deepstack.http.Response;
import com.deepstack.model.Address;
import com.deepstack.util.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.util.TextUtils;

import static com.deepstack.util.Constants.SLASH;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends BasicInterface {

  public static final String CUSTOMER_URL = "/customer";

  /**
   * Initialize the client.
   *
   * @param config model {@link Config} with all configuration parameters
   * @throws AuthenticationException In case of a bad defined secret API or APP keys
   */
  public Customer(Config config) throws AuthenticationException {
    initialize(config);
  }

  /** The customer unique identifier. */
  private String id;

  /** The company identifier. */
  @JsonProperty("client_id")
  private String clientId;

  /** The customer's unique identifier within the company's system. */
  @JsonProperty("client_customer_id")
  private String clientCustomerId;

  /** The customer's contact first name. */
  @JsonProperty("first_name")
  private String firstName;

  /** The customer's contact last name. */
  @JsonProperty("last_name")
  private String lastName;

  /** The customer's contact address. */
  @JsonProperty("address")
  private Address address;

  /** The customer's contact phone number. */
  @JsonProperty("phone")
  private String phone;

  /** The customer's contact email address. */
  @JsonProperty("email")
  private String email;

  /**
   * Creates a new customer object.
   *
   * @return the new {@link Customer} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public Customer create() throws IOException, DeepStackException {
    return create(null);
  }

  /**
   * Creates a new customer object with request options.
   *
   * @param requestOptions The {@link RequestOptions} object. Can accept null value.
   * @return the new {@link Customer} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public Customer create(RequestOptions requestOptions) throws IOException, DeepStackException {
    String customer = this.toJson();
    this.addHmacHeader(customer, requestOptions, "POST");
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint(CUSTOMER_URL)
            .method(Method.POST)
            .headers(getRequestHeaders())
            .body(customer)
            .options(requestOptions)
            .build();

    Response response = api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return Customer.builder().build().fromJson(response.getBody(), Customer.class);
  }

  /**
   * Updates the specified customer.
   *
   * @param id {@link Customer} identifier
   * @return the updated {@link Customer} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public Customer update(String id) throws IOException, DeepStackException {
    return update(id, null);
  }

  /**
   * Updates the specified customer with request options.
   *
   * @param id {@link Customer} identifier
   * @param requestOptions {@link RequestOptions}
   * @return the updated {@link Customer} instance
   * @throws IOException In case of a JSON marshal error
   * @throws DeepStackException In case of an API error
   */
  public Customer update(String id, RequestOptions requestOptions)
      throws IOException, DeepStackException {
    getRequestHeaders().clear();
    String endpoint = URI.create(CUSTOMER_URL.concat(SLASH).concat(urlEncodeId(id))).toString();
    String customer = this.toJson();
    this.addHmacHeader(customer, requestOptions, "PUT");
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint(endpoint)
            .method(Method.PUT)
            .headers(getRequestHeaders())
            .body(customer)
            .options(requestOptions)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return Customer.builder().build().fromJson(response.getBody(), Customer.class);
  }

  /**
   * Permanently deletes a customer.
   *
   * @param id {@link Customer} identifier
   * @throws DeepStackException In case of an API error
   */
  public void delete(String id) throws DeepStackException {
    delete(id, null);
  }

  /**
   * Permanently deletes a customer with request options.
   *
   * @param id {@link Customer} identifier
   * @param requestOptions {@link RequestOptions}
   * @throws DeepStackException In case of an API error
   */
  public void delete(String id, RequestOptions requestOptions) throws DeepStackException {
    this.addHmacHeader("", requestOptions, "DELETE");
    String endpoint = URI.create(CUSTOMER_URL.concat(SLASH).concat(urlEncodeId(id))).toString();
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint(endpoint)
            .method(Method.DELETE)
            .headers(getRequestHeaders())
            .body("")
            .options(requestOptions)
            .build();

    this.api(request);
  }

  /**
   * Returns a list of customers.
   *
   * @param <T> Class type
   * @return list of {@link Customer}
   * @throws DeepStackException In case of an API error
   * @throws IOException In case of a JSON marshal error
   */
  public <T> T list() throws DeepStackException, IOException {
    return list(null, null);
  }

  /**
   * Returns a list of customers with request options.
   *
   * @param queryParams Query parameters for page size and index
   * @param requestOptions {@link RequestOptions}
   * @return list of {@link Customer}
   * @throws DeepStackException In case of an API error
   * @throws IOException In case of a JSON marshal error
   * @param <T> Class type
   */
  public <T> T list(Map<String, String> queryParams, RequestOptions requestOptions)
      throws DeepStackException, IOException {
    getRequestHeaders().clear();
    this.addAuthHeader(requestOptions);
    String endpoint = URI.create(CUSTOMER_URL).toString();
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint(endpoint)
            .method(Method.GET)
            .headers(getRequestHeaders())
            .queryParams(queryParams)
            .options(requestOptions)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }
    return (T) JsonUtils.convertFromJsonToList(response.getBody(), Customer.class);
  }

  /**
   * Retrieves the details of an existing customer.
   *
   * @param id {@link Customer} identifier
   * @param <T> Class type
   * @return the {@link Customer} instance
   * @throws DeepStackException In case of an API error
   * @throws IOException In case of a JSON marshal error
   */
  public <T> T retrieve(String id) throws DeepStackException, IOException {
    return retrieve(id, null);
  }

  /**
   * Retrieves the details of an existing customer with request options.
   *
   * @param id {@link Customer} identifier
   * @param requestOptions {@link RequestOptions}
   * @param <T> Class type
   * @return the {@link Customer} instance or list of {@link Customer} if Customer identifier is
   *     null or empty
   * @throws DeepStackException In case of an API error
   * @throws IOException In case of a JSON marshal error
   */
  public <T> T retrieve(String id, RequestOptions requestOptions)
      throws DeepStackException, IOException {
    getRequestHeaders().clear();
    this.addAuthHeader(requestOptions);
    String endpoint = URI.create(CUSTOMER_URL.concat(SLASH).concat(urlEncodeId(id))).toString();
    Request request =
        Request.builder()
            .baseUri(getBaseUrl())
            .endpoint(endpoint)
            .method(Method.GET)
            .headers(getRequestHeaders())
            .options(requestOptions)
            .build();

    Response response = this.api(request);
    if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
      throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
    }

    if (!TextUtils.isBlank(id)) {
      return (T) JsonUtils.convertFromJsonToObject(response.getBody(), Customer.class);
    }

    return (T) JsonUtils.convertFromJsonToList(response.getBody(), Customer.class);
  }
}

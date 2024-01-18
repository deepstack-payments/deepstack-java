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
import com.deepstack.model.BillingContact;
import com.deepstack.model.CreditCard;
import com.deepstack.model.PaymentInstrumentToken;
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

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrument extends BasicInterface {
    public static final String CARD_TYPE = "creditcard";
    public static final String PAYMENTINSTRUMENT_URL = "/paymentinstrument";

    /**
     * The payment instrument unique identifier.
     */
    private String id;

    /**
     * The payment instrument's type.
     */
    private String type;

    /**
     * The credit card.
     */
    @JsonProperty("creditcard")
    private CreditCard creditCard;

    /**
     * The billing contact.
     */
    @JsonProperty("billing_contact")
    private BillingContact billingContact;

    /**
     * The customer's unique identifier.
     */
    @JsonProperty("customer_id")
    private String customerId;

    /**
     * The customer's unique identifier within the company's system.
     */
    @JsonProperty("client_customer_id")
    private String clientCustomerId;

    /**
     * The company identifier for the payment method. The company application never needs to submit
     * this. It is resolved based on their API credentials, or the JWT when submitted via JS SDK.
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * The data.
     */
    @JsonProperty("data")
    private String data;

    public String getType() {
        return TextUtils.isBlank(type) ? CARD_TYPE : type;
    }

    /**
     * Initialize the client.
     *
     * @param config model {@link Config} with all configuration parameters.
     * @throws AuthenticationException In case of a bad defined secret API or APP keys
     */
    public PaymentInstrument(Config config) throws AuthenticationException {
        initialize(config);
    }

    /**
     * Creates a new payment instrument object.
     *
     * @return the new {@link PaymentInstrument} instance
     * @throws IOException           In case of a JSON marshal error
     * @throws DeepStackException In case of an API error
     */
    public PaymentInstrumentToken create() throws IOException, DeepStackException {
        return create(null);
    }

    /**
     * Creates a new payment instrument object with request options.
     *
     * @param requestOptions {@link RequestOptions}
     * @return the new {@link PaymentInstrument} instance
     * @throws IOException           In case of a JSON marshal error
     * @throws DeepStackException In case of an API error
     */
    public PaymentInstrumentToken create(RequestOptions requestOptions)
            throws IOException, DeepStackException {
        String paymentInstrument = this.toJson();
        this.addHmacHeader(paymentInstrument, requestOptions, "POST");
        Request request =
                Request.builder()
                        .baseUri(getBaseUrl())
                        .endpoint(PAYMENTINSTRUMENT_URL)
                        .method(Method.POST)
                        .headers(getRequestHeaders())
                        .body(paymentInstrument)
                        .options(requestOptions)
                        .build();

        Response response = this.api(request);
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
        }
        return PaymentInstrumentToken.builder()
                .build()
                .fromJson(response.getBody(), PaymentInstrumentToken.class);
    }

    /**
     * Updates the specified payment instrument.
     *
     * @param id {@link PaymentInstrument} identifier
     * @return the updated {@link PaymentInstrument} instance
     * @throws IOException           In case of a JSON marshal error
     * @throws DeepStackException In case of an API error
     */
    public PaymentInstrumentToken update(String id) throws IOException, DeepStackException {
        return update(id, null);
    }

    /**
     * Updates the specified payment instrument with request options.
     *
     * @param id             {@link PaymentInstrument} identifier
     * @param requestOptions {@link RequestOptions}
     * @return the updated {@link PaymentInstrument} instance
     * @throws IOException           In case of a JSON marshal error
     * @throws DeepStackException In case of an API error
     */
    public PaymentInstrumentToken update(String id, RequestOptions requestOptions)
            throws IOException, DeepStackException {
        getRequestHeaders().clear();
        String endpoint = URI.create(PAYMENTINSTRUMENT_URL.concat(urlEncodeId(id))).toString();
        String paymentInstrument = this.toJson();
        this.addHmacHeader(paymentInstrument, requestOptions, "PUT");
        Request request =
                Request.builder()
                        .baseUri(getBaseUrl())
                        .endpoint(endpoint)
                        .method(Method.PUT)
                        .headers(getRequestHeaders())
                        .body(paymentInstrument)
                        .options(requestOptions)
                        .build();

        Response response = this.api(request);
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
        }
        return PaymentInstrumentToken.builder()
                .build()
                .fromJson(response.getBody(), PaymentInstrumentToken.class);
    }

    /**
     * Permanently deletes a payment instrument.
     *
     * @param id {@link PaymentInstrument} identifier
     * @throws DeepStackException In case of an API error
     */
    public void delete(String id) throws DeepStackException {
        delete(id, null);
    }

    /**
     * Permanently deletes a payment instrument with request options.
     *
     * @param id             {@link PaymentInstrument} identifier
     * @param requestOptions {@link RequestOptions}
     * @throws DeepStackException In case of an API error
     */
    public void delete(String id, RequestOptions requestOptions) throws DeepStackException {
        this.addHmacHeader("", requestOptions, "DELETE");
        String endpoint = URI.create(PAYMENTINSTRUMENT_URL.concat(urlEncodeId(id))).toString();
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
     * Returns a list of payment instruments by customer.
     *
     * @param customerId {@link Customer} identifier
     * @param <T>        Class type
     * @return list of {@link PaymentInstrument}
     * @throws DeepStackException In case of an API error
     * @throws IOException           In case of a JSON marshal error
     */
    public <T> T list(String customerId) throws DeepStackException, IOException {
        return list(customerId, null, null);
    }

    /**
     * Returns a list of payment instruments by customer with request options.
     *
     * @param customerId     {@link Customer} identifier
     * @param queryParams    Query parameters for page size and index
     * @param requestOptions {@link RequestOptions}
     * @param <T>            Class type
     * @return list of {@link PaymentInstrument}
     * @throws DeepStackException In case of an API error
     * @throws IOException           In case of a JSON marshal error
     */
    public <T> T list(
            String customerId, Map<String, String> queryParams, RequestOptions requestOptions)
            throws DeepStackException, IOException {
        getRequestHeaders().clear();
        this.addAuthHeader(requestOptions);
        String endpoint =
                URI.create(PAYMENTINSTRUMENT_URL.concat("list/").concat(urlEncodeId(customerId)))
                        .toString();
        Request request =
                Request.builder()
                        .baseUri(getBaseUrl())
                        .endpoint(endpoint)
                        .method(Method.GET)
                        .headers(getRequestHeaders())
                        .options(requestOptions)
                        .queryParams(queryParams)
                        .build();

        Response response = this.api(request);
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new InvalidRequestException(400, ErrorMessage.BAD_REQUEST.getLabel(), null, null);
        }
        return (T) JsonUtils.convertFromJsonToList(response.getBody(), PaymentInstrumentToken.class);
    }

    /**
     * Retrieves the details of an existing payment instrument.
     *
     * @param id  {@link PaymentInstrument} identifier
     * @param <T> Class type
     * @return the {@link PaymentInstrument} instance
     * @throws DeepStackException In case of an API error
     * @throws IOException           In case of a JSON marshal error
     */
    public <T> T retrieve(String id) throws DeepStackException, IOException {
        return retrieve(id, null);
    }

    /**
     * Retrieves the details of an existing payment instrument with request options.
     *
     * @param id             {@link PaymentInstrument} identifier
     * @param requestOptions {@link RequestOptions}
     * @param <T>            Class type
     * @return the {@link PaymentInstrument} instance
     * @throws DeepStackException In case of an API error
     * @throws IOException           In case of a JSON marshal error
     */
    public <T> T retrieve(String id, RequestOptions requestOptions)
            throws DeepStackException, IOException {
        getRequestHeaders().clear();
        this.addAuthHeader(requestOptions);
        String endpoint = URI.create(PAYMENTINSTRUMENT_URL.concat(urlEncodeId(id))).toString();
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
        return (T) JsonUtils.convertFromJsonToObject(response.getBody(), PaymentInstrumentToken.class);
    }
}

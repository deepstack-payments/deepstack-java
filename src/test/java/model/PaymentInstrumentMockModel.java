package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.service.Customer;
import com.deepstack.service.PaymentInstrument;
import com.deepstack.util.JsonUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaymentInstrumentMockModel {

  private static final Faker faker = new Faker();

  public static PaymentInstrument paymentInstrument() {
    return PaymentInstrument.builder().build();
  }

  public static String paymentInstrumentCreateGloballyPaidError() throws JsonProcessingException {
    Map<String, Object> globallyPaidError = new HashMap<>();
    Map<String, Object> customerError = new HashMap<>();
    customerError.put("client_customer_id", Collections.singletonList("The input was not valid."));
    globallyPaidError.put("errors", customerError);
    globallyPaidError.put("type", faker.lorem().word());
    globallyPaidError.put("title", faker.lorem().sentence());
    globallyPaidError.put("status", 400);
    globallyPaidError.put("traceId", faker.lorem().word());

    return JsonUtils.convertFromObjectToJson(globallyPaidError);
  }

  public static List<PaymentInstrumentToken> paymentInstrumentList(Customer customer) {
    return Stream.of(
            GloballyPaidMockModel.paymentInstrumentTokenWithCustomer(
                GloballyPaidMockModel.tokenRequest(), customer),
            GloballyPaidMockModel.paymentInstrumentTokenWithCustomer(
                GloballyPaidMockModel.tokenRequest(), customer),
            GloballyPaidMockModel.paymentInstrumentTokenWithCustomer(
                GloballyPaidMockModel.tokenRequest(), customer))
        .collect(Collectors.toList());
  }
}

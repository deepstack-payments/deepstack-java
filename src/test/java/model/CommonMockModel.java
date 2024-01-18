package model;

import com.github.javafaker.Faker;
import com.deepstack.http.Response;
import com.deepstack.model.Address;
import com.deepstack.model.BillingContact;
import com.deepstack.model.CreditCard;
import com.deepstack.model.Entity;
import com.deepstack.model.common.PaymentInstrumentCard;
import com.deepstack.service.PaymentInstrument;
import com.deepstack.util.JsonUtils;
import java.io.IOException;
import java.util.List;

public class CommonMockModel {
  private static final Faker faker = new Faker();

  public static Address address() {
    return Address.builder()
        .Line1(faker.address().streetAddress())
        .City(faker.address().city())
        .State(faker.address().state())
        .PostalCode(faker.address().zipCode())
        .CountryCode(faker.address().countryCode())
        .build();
  }

  public static BillingContact billingContact() {
    return BillingContact.builder()
        .FirstName(faker.name().firstName())
        .LastName(faker.name().lastName())
        .Address(address())
        .Phone(faker.phoneNumber().phoneNumber())
        .Email(faker.internet().emailAddress())
        .build();
  }

  public static CreditCard creditCard() {
    return CreditCard.builder()
        .Number(String.valueOf(faker.number().digits(16)))
        .Expiration(String.valueOf(faker.number().digits(4)))
        .CVV(String.valueOf(faker.number().digits(3)))
        .build();
  }

  public static PaymentInstrument paymentInstrument(boolean withCardType) {
    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .creditCard(creditCard())
            .billingContact(billingContact())
            .customerId(faker.lorem().word())
            .clientCustomerId(faker.lorem().word())
            .build();
    if (withCardType) {
      paymentInstrument.setType("creditcard");
    }

    return paymentInstrument;
  }

  public static PaymentInstrumentCard paymentInstrumentCard(){

    PaymentInstrumentCard paymentInstrumentCard =
            PaymentInstrumentCard.builder()
                    .CreditCard(creditCard())
                    .BillingContact(billingContact())
                    .CustomerId(faker.lorem().word())
                    .ClientCustomerId(faker.lorem().word())
                    .build();

    return paymentInstrumentCard;

  }

  public static <T extends Entity> Response response200WithCode00(T model) throws IOException {
    return Response.builder().statusCode(200).body(model.toJson()).build();
  }

  public static <T extends Entity> Response response200WithCode00(List<T> model)
      throws IOException {
    return Response.builder()
        .statusCode(200)
        .body(JsonUtils.convertFromObjectToJson(model))
        .build();
  }
}

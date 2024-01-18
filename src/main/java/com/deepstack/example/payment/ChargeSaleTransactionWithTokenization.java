package com.deepstack.example.payment;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.http.RequestOptions;
import com.deepstack.model.*;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.common.PaymentInstrumentCard;
import com.deepstack.model.common.PaymentSourceRawCard;
import com.deepstack.model.common.TransactionMeta;
import com.deepstack.model.common.TransactionParameters;
import com.deepstack.service.DeepStack;

import java.io.IOException;

public class ChargeSaleTransactionWithTokenization {
  public static void main(String[] args) throws IOException, DeepStackException {
    try {
      DeepStack deepStack =
          new DeepStack(
              Config.builder()
                  .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                  .appId(System.getenv("APP_ID"))
                  .sharedSecret(System.getenv("SHARED_SECRET"))
                  .sandbox(System.getenv("USE_SANDBOX"))
                  .build());

      Address address =
          Address.builder()
              .Line1("Sun city St")
              .City("NYC")
              .State("NY")
              .PostalCode("12345")
              .CountryCode("US")
              .build();

      BillingContact billingContact =
          BillingContact.builder()
              .FirstName("New Jane")
              .LastName("Doe Tester")
              .Address(address)
              .Phone("614-340-0823")
              .Email("test@test.com")
              .build();

      CreditCard creditCard =
          CreditCard.builder().Number("4847182731147117").Expiration("0627").CVV("361").build();

      PaymentInstrumentCard paymentInstrument =
          PaymentInstrumentCard.builder()
              .CreditCard(creditCard)
              .BillingContact(billingContact)
              .build();

      TokenRequest tokenRequest =
          TokenRequest.builder().PaymentInstrumentRequest(paymentInstrument).build();

      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(50 * 1000).build();

      PaymentInstrumentToken paymentInstrumentToken =
          deepStack.token(tokenRequest, requestOptions);

      if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

        ChargeRequest chargeRequest =
            ChargeRequest.builder()
                    .Source(PaymentSourceRawCard.builder()
                            .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                            .build())
                    .Params(TransactionParameters.builder()
                            .Amount(130)
                            .SavePaymentInstrument(false)
                            .Capture(true)
                            .CurrencyCode("USD")
                            .build())
                    .Meta(TransactionMeta.builder()
                            .ClientCustomerID("4444687")
                            .ClientInvoiceID("123456")
                            .ClientTransactionDescription("ChargeWithToken new Hmac - Test")
                            .ClientTransactionID("154896575")
                            .build())
                .build();

        ChargeResponse chargeResponse = DeepStack.builder().build().charge(chargeRequest);
        System.out.println(chargeResponse);
      }
    } catch (DeepStackException e) {
      System.out.println(
          "ChargeSaleTrans ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
      throw e;
    }
  }
}

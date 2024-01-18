package com.deepstack.example.paymentinstrument;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.service.PaymentInstrument;
import java.io.IOException;

public class PaymentInstrumentRetrieveNotFound {
  public static void main(String[] args) throws IOException, DeepStackException {

    PaymentInstrument paymentInstrument =
        new PaymentInstrument(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    try {
      PaymentInstrumentToken retrievedPaymentInstrument = paymentInstrument.retrieve("123455");
      System.out.println("Retrieved PaymentInstrument: " + retrievedPaymentInstrument);
    } catch (DeepStackException e) {
      System.out.println(
          "PaymentInstrument retrieve ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
    }
  }
}

package com.deepstack.example.payment;

import com.deepstack.example.MockModel;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.http.RequestOptions;
import com.deepstack.model.ChargeResponse;
import com.deepstack.model.TokenRequest;
import com.deepstack.service.DeepStack;

import java.io.IOException;

public class ChargeSaleTransWithRequestOptions {
  public static void main(String[] args) throws IOException, DeepStackException {

    DeepStack deepStack =
        new DeepStack(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    TokenRequest tokenRequest =
        TokenRequest.builder().PaymentInstrumentRequest(MockModel.getPaymentInstrumentCard()).build();

    try {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).publishableApiKey("skdjskjfs").build();
      deepStack.token(tokenRequest, requestOptions);
    } catch (DeepStackException e) {
      System.out.println(
          "Tokenization ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
    }

    try {
      ChargeResponse chargeResponse =
          deepStack.charge(
              MockModel.getChargeRequestWithClientInfo(deepStack.token(tokenRequest).getId()));
      System.out.println("chargeResponse: " + chargeResponse);
    } catch (DeepStackException e) {
      System.out.println(
          "ChargeSaleTrans ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
    }
  }
}

package com.deepstack.example.payment;

import com.deepstack.example.MockModel;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.model.*;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.common.PaymentInstrumentCard;
import com.deepstack.service.DeepStack;

import java.io.IOException;

public class RefundChargeAuthenticationTrans {
  public static void main(String[] args) throws IOException, DeepStackException {

    DeepStack deepStack =
        new DeepStack(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    PaymentInstrumentCard paymentInstrument =
        PaymentInstrumentCard.builder()
//            .type("creditcard")
            .CreditCard(MockModel.getCreditCard())
            .BillingContact(MockModel.getBillingContact())
            .build();

    TokenRequest tokenRequest = TokenRequest.builder().PaymentInstrumentRequest(paymentInstrument).build();

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      paymentInstrumentToken = deepStack.token(tokenRequest);
    } catch (DeepStackException e) {
      System.out.println(
          "Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
    }
    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

      // call charge with the following capture call
      ChargeResponse chargeResponse =
          deepStack.charge(
              MockModel.getChargeRequestWithCaptureFalse(paymentInstrumentToken.getId()));

      if (chargeResponse != null && !chargeResponse.getID().isEmpty()) {
        System.out.println(chargeResponse.toString());
        // call refund
        RefundResponse refundResponse =
            DeepStack.builder()
                .build()
                .refund(
                    RefundRequest.builder()
                        .Charge(chargeResponse.getID())
                        .Amount(chargeResponse.getAmount())
                        .build());
        System.out.println("Refund: " + refundResponse);
      }
    }
  }
}

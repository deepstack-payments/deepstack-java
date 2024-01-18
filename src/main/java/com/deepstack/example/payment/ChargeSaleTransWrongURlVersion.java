package com.deepstack.example.payment;

import com.deepstack.example.MockModel;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.BasicInterface;
import com.deepstack.http.Config;
import com.deepstack.http.RequestOptions;
import com.deepstack.model.ChargeResponse;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.model.TokenRequest;
import com.deepstack.model.common.PaymentInstrumentCard;
import com.deepstack.service.DeepStack;
import com.deepstack.util.JsonUtils;
import java.io.IOException;
import java.util.Map;

import static com.deepstack.util.Constants.VERSION;

public class ChargeSaleTransWrongURlVersion {
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
            .CreditCard(MockModel.getCreditCard())
            .BillingContact(MockModel.getBillingContact())
            .build();

    TokenRequest tokenRequest = TokenRequest.builder().PaymentInstrumentRequest(paymentInstrument).build();

    // set wrong API version
    BasicInterface.setVersion(VERSION.concat("3"));

    PaymentInstrumentToken paymentInstrumentToken = null;
    try {
      RequestOptions requestOptions = RequestOptions.builder().connectTimeout(30 * 1000).build();
      paymentInstrumentToken = deepStack.token(tokenRequest, requestOptions);
    } catch (DeepStackException e) {
      System.out.println(
          "*** Tokenization Error *** \nCode: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());

      if (!e.getDeepStackError().isEmpty()) {
        Map<String, Object> apiError =
            JsonUtils.convertFromJsonToObject(
                JsonUtils.convertFromObjectToJson(e.getDeepStackError().get("error")),
                Map.class);
        System.out.println(
            "\n*** Tokenization Api Error *** \nCode: "
                + apiError.get("code")
                + ", Msg: "
                + apiError.get("message"));
      }
    }
    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {

      try {
        ChargeResponse chargeResponse =
            deepStack.charge(
                MockModel.getChargeRequestWithClientInfo(paymentInstrumentToken.getId()));
        System.out.println(chargeResponse.toString());
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
}

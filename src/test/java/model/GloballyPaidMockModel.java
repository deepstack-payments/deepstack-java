package model;

import com.github.javafaker.Faker;
import com.deepstack.model.*;
import com.deepstack.model.common.PaymentSourceRawCard;
import com.deepstack.model.common.TransactionMeta;
import com.deepstack.model.common.TransactionParameters;
import com.deepstack.service.Customer;

public class GloballyPaidMockModel {

  public static final String APPROVED = "Approved";
  public static final String RESPONSE_CODE = "00";
  private static final Faker faker = new Faker();

  public static ChargeRequest getChargeRequestWithCaptureTrueAndClientInfo() {
    return ChargeRequest.builder()
            .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(faker.lorem().fixedString(20)).build())
                    .build())
            .Params(TransactionParameters.builder()
                    .Amount(faker.number().numberBetween(100, 200))
                    .CurrencyCode(faker.currency().code())
                    .Capture(Boolean.TRUE)
                    .SavePaymentInstrument(Boolean.FALSE)
                    .build())
//        .currencyCode(faker.currency().code())
            .Meta(TransactionMeta.builder()
                    .ClientTransactionDescription(String.valueOf(faker.number().numberBetween(1, 10)))
                    .ClientTransactionID(String.valueOf(faker.number().numberBetween(1, 10)))
                    .ClientInvoiceID(String.valueOf(faker.number().numberBetween(1, 10)))
                    .ClientCustomerID(String.valueOf(faker.number().numberBetween(1, 10)))
                    .build())
//        .capture(Boolean.TRUE)
//        .savePaymentInstrument(Boolean.FALSE)
        .build();
  }

  public static ChargeRequest chargeRequestWithoutSource() {
    return ChargeRequest.builder().Params(TransactionParameters.builder().Capture(true).build()).build();
  }

  public static ChargeRequest chargeRequestOnlyWithSource() {
    return ChargeRequest.builder().Source(PaymentSourceRawCard.builder()
            .CreditCard(CreditCard.builder().Number(faker.lorem().fixedString(20)).build())
            .build()).build();
  }

  public static TokenRequest tokenRequest() {
    return TokenRequest.builder()
        .PaymentInstrumentRequest(CommonMockModel.paymentInstrumentCard())
        .build();
  }

  public static ChargeResponse chargeResponseForCaptureTrueAndClientInfo(Integer amount) {
    return ChargeResponse.builder()
        .ID(faker.lorem().word())
        .ResponseCode(RESPONSE_CODE)
        .Message(APPROVED)
        .approved(Boolean.TRUE)
        .Amount(amount)
        .build();
  }

  public static PaymentInstrumentToken paymentInstrumentToken(TokenRequest tokenRequest) {
    return PaymentInstrumentToken.builder()
        .Id(faker.lorem().word())
        .BillingContact(tokenRequest.getPaymentInstrumentRequest().getBillingContact())
        .ClientCustomerId(tokenRequest.getPaymentInstrumentRequest().getClientCustomerId())
        .ClientId(tokenRequest.getPaymentInstrumentRequest().getClientId())
        .CustomerId(tokenRequest.getPaymentInstrumentRequest().getCustomerId())
        .Brand(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getBrand())
        .Expiration(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getExpiration())
        .LastFour(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getLastFour())
        .Type(tokenRequest.getPaymentInstrumentRequest().getType())
        .build();
  }

  public static PaymentInstrumentToken paymentInstrumentTokenWithCustomer(
      TokenRequest tokenRequest, Customer customer) {
    return PaymentInstrumentToken.builder()
        .Id(faker.lorem().word())
        .BillingContact(tokenRequest.getPaymentInstrumentRequest().getBillingContact())
        .ClientCustomerId(tokenRequest.getPaymentInstrumentRequest().getClientCustomerId())
        .ClientId(tokenRequest.getPaymentInstrumentRequest().getClientId())
        .CustomerId(customer.getId())
        .Brand(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getBrand())
        .Expiration(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getExpiration())
        .LastFour(tokenRequest.getPaymentInstrumentRequest().getCreditCard().getLastFour())
        .Type(tokenRequest.getPaymentInstrumentRequest().getType())
        .build();
  }

  public static CaptureRequest captureRequest() {
    return CaptureRequest.builder()
        .Amount(faker.number().numberBetween(100, 200))
        .Charge(faker.lorem().fixedString(20))
        .build();
  }

  public static CaptureResponse captureResponse(CaptureRequest captureRequest) {
    return CaptureResponse.builder()
        .Amount(captureRequest.getAmount())
        .ChargeTransactionID(captureRequest.getCharge())
        .ResponseCode(RESPONSE_CODE)
        .Message(APPROVED)
        .Approved(Boolean.TRUE)
        .isRecurring(false)
        .build();
  }

  public static RefundRequest refundRequest() {
    return RefundRequest.builder()
        .Amount(faker.number().numberBetween(100, 200))
        .Charge(faker.lorem().fixedString(20))
        .build();
  }

  public static RefundResponse refundResponse(RefundRequest refundRequest) {
    return RefundResponse.builder()
        .Amount(refundRequest.getAmount())
        .ChargeTransactionID(refundRequest.getCharge())
        .ResponseCode(RESPONSE_CODE)
        .Message(APPROVED)
        .Approved(Boolean.TRUE)
        .build();
  }
}

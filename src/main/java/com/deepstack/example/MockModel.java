package com.deepstack.example;

import com.deepstack.model.*;
import com.deepstack.model.PaymentInstrumentToken;
//import com.globallypaid.model.common.PaymentSource;
import com.deepstack.model.common.PaymentInstrumentCard;
import com.deepstack.model.common.PaymentSourceRawCard;
import com.deepstack.model.common.TransactionMeta;
import com.deepstack.model.common.TransactionParameters;
import com.deepstack.service.PaymentInstrument;

public class MockModel {
  private MockModel() {
    throw new UnsupportedOperationException("MockModel");
  }

  public static Address getAddress() {
    return Address.builder()
        .Line1("Sun city St")
        .City("NYC")
        .State("NY")
        .PostalCode("12345")
        .CountryCode("US")
        .build();
  }

  public static BillingContact getBillingContact() {
    return BillingContact.builder()
        .FirstName("New Jane")
        .LastName("Doe Tester")
        .Address(getAddress())
        .Phone("614-340-0823")
        .Email("test@test.com")
        .build();
  }

  public static BillingContact getBillingContactWithoutAddress() {
    return BillingContact.builder().FirstName("New Jane").LastName("Doe").build();
  }

  public static BillingContact getBillingContactWithAddressNull() {
    return BillingContact.builder().FirstName("New Jane").LastName("Doe").Address(null).build();
  }

  public static BillingContact getBillingContactWithAddressEmpty() {
    return BillingContact.builder()
        .FirstName("New Jane")
        .LastName("Doe")
        .Address(Address.builder().build())
        .build();
  }

  public static CreditCard getCreditCard() {
    return CreditCard.builder().Number("4847182731147117").Expiration("0627").CVV("361").build();
  }

  public static PaymentInstrument getPaymentInstrument(boolean withCardType) {
    PaymentInstrument paymentInstrument =
        PaymentInstrument.builder()
            .creditCard(getCreditCard())
            .billingContact(getBillingContact())
            .build();
    if (withCardType) {
      paymentInstrument.setType("creditcard");
    }

    return paymentInstrument;
  }

  public static PaymentInstrumentCard getPaymentInstrumentCard(){
    PaymentInstrumentCard paymentInstrumentCard =
            PaymentInstrumentCard.builder()
                    .CreditCard(getCreditCard())
                    .BillingContact(getBillingContact())
                    .build();
    return paymentInstrumentCard;
  }

  public static ChargeRequest getChargeRequestWithClientInfo(String paymentInstrumentId) {

    return ChargeRequest.builder()
        .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(paymentInstrumentId).build())
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
  }

  public static ChargeRequest getChargeRequestWithoutClientInfo(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
            .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                    .build())
            .Params(TransactionParameters.builder()
                    .Amount(100)
                    .SavePaymentInstrument(false)
                    .Capture(true)
                    .CurrencyCode("USD")
                    .build())
        .build();
  }

  public static ChargeRequest getChargeRequestWithEmptyClientInfo(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
            .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                    .build())
            .Params(TransactionParameters.builder()
                    .Amount(100)
                    .SavePaymentInstrument(false)
                    .Capture(true)
                    .CurrencyCode("USD")
                    .build())
            .Meta(TransactionMeta.builder()
                    .ClientCustomerID("")
                    .ClientInvoiceID("")
                    .ClientTransactionDescription("")
                    .ClientTransactionID("")
                    .build())
        .build();
  }

  public static ChargeRequest getChargeRequestWithCaptureFalse(String paymentInstrumentId) {
    return ChargeRequest.builder()
            .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(paymentInstrumentId).build())
                    .build())
            .Params(TransactionParameters.builder()
                    .Amount(100)
                    .SavePaymentInstrument(false)
                    .Capture(false)
                    .CurrencyCode("USD")
                    .build())
            .Meta(TransactionMeta.builder()
                    .ClientCustomerID("4444687")
                    .ClientInvoiceID("123456")
                    .ClientTransactionDescription("ChargeWithToken new Hmac - Test")
                    .ClientTransactionID("154896575")
                    .build())
        .build();
  }

  public static ChargeRequest getChargeRequestWithCaptureTrueAndPaymentInstrument(
      PaymentInstrumentToken paymentInstrumentToken) {
    return ChargeRequest.builder()
            .Source(PaymentSourceRawCard.builder()
                    .CreditCard(CreditCard.builder().Number(paymentInstrumentToken.getId()).build())
                    .build())
            .Params(TransactionParameters.builder()
                    .Amount(100)
                    .SavePaymentInstrument(true)
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
  }
}

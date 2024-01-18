package com.deepstack.example.paymentinstrument;

import com.deepstack.example.MockModel;
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.model.PaymentInstrumentToken;
import com.deepstack.service.Customer;
import com.deepstack.service.PaymentInstrument;

import java.io.IOException;
import java.util.UUID;

public class PaymentInstrumentUpdate {
    public static void main(String[] args) throws IOException, DeepStackException {

        new PaymentInstrument(
                Config.builder()
                        .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                        .appId(System.getenv("APP_ID"))
                        .sharedSecret(System.getenv("SHARED_SECRET"))
                        .sandbox(System.getenv("USE_SANDBOX"))
                        .build());

        try {
            String uuid = UUID.randomUUID().toString();
            Customer customer =
                    Customer.builder()
                            .clientCustomerId("Jane Doe payment instrument update ".concat(uuid)).firstName("Jane ".concat(uuid)).lastName("Doe ".concat(uuid))
                            .build()
                            .create();
            System.out.println("Created customer: " + customer);

            PaymentInstrument paymentInstrument =
                    PaymentInstrument.builder()
                            .creditCard(MockModel.getCreditCard())
                            .customerId(customer.getId())
                            .build();
            PaymentInstrumentToken paymentInstrumentToken = paymentInstrument.create();
            System.out.println("Created PaymentInstrument: " + paymentInstrumentToken);

            PaymentInstrumentToken retrievedPaymentInstrument =
                    PaymentInstrument.builder().build().retrieve(paymentInstrumentToken.getId());
            System.out.println("Retrieved PaymentInstrument: " + retrievedPaymentInstrument);

            //      PaymentInstrumentToken updatedPaymentInstrumentToken =
            //          PaymentInstrument.builder()
            //              .id(retrievedPaymentInstrument.getId())
            //              .clientCustomerId(retrievedPaymentInstrument.getClientCustomerId())
            //              .type(retrievedPaymentInstrument.getType())
            //              .customerId("123456")
            //              .billingContact(retrievedPaymentInstrument.getBillingContact())
            //              .clientId(retrievedPaymentInstrument.getClientId())
            //              .build()
            //              .update(retrievedPaymentInstrument.getId());

            paymentInstrument.setId(retrievedPaymentInstrument.getId());
            paymentInstrument.setClientCustomerId("456789");
            PaymentInstrumentToken updatedPaymentInstrumentToken =
                    paymentInstrument.update(retrievedPaymentInstrument.getId());
            System.out.println("Updated PaymentInstrument: " + updatedPaymentInstrumentToken);
        } catch (DeepStackException e) {
            System.out.println(
                    "PaymentInstrument update ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getDeepStackError());
        }
    }
}

package com.deepstack.example.customer;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.service.Customer;

import java.io.IOException;
import java.util.UUID;

public class CustomerUpdate {
    public static void main(String[] args) throws IOException, DeepStackException {

        new Customer(
                Config.builder()
                        .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                        .appId(System.getenv("APP_ID"))
                        .sharedSecret(System.getenv("SHARED_SECRET"))
                        .sandbox(System.getenv("USE_SANDBOX"))
                        .build());

        try {
            String uuid = UUID.randomUUID().toString();
            Customer createdCustomer =
                    Customer.builder()
                            .clientCustomerId("Jane Doe ".concat(uuid))
                            .firstName("Jane ".concat(uuid)).lastName("Doe ".concat(uuid))
                            .build()
                            .create();
            System.out.println("Created customer: " + createdCustomer);

            Customer retrievedCustomer = Customer.builder().build().retrieve(createdCustomer.getId());
            System.out.println("Retrieved customer: " + retrievedCustomer);

            retrievedCustomer.setLastName("Doe updated ".concat(uuid));
            Customer updatedCustomer = retrievedCustomer.update(retrievedCustomer.getId());
            System.out.println("Updated customer: " + updatedCustomer);
        } catch (DeepStackException e) {
            System.out.println(
                    "Customer update ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getDeepStackError());
        }
    }
}

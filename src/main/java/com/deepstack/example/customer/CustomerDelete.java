package com.deepstack.example.customer;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.service.Customer;

import java.io.IOException;
import java.util.UUID;

public class CustomerDelete {
    public static void main(String[] args) throws IOException, DeepStackException {

        Customer customer =
                new Customer(
                        Config.builder()
                                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                                .appId(System.getenv("APP_ID"))
                                .sharedSecret(System.getenv("SHARED_SECRET"))
                                .sandbox(System.getenv("USE_SANDBOX"))
                                .build());

        try {
            customer.setClientCustomerId("Jane Doe delete ".concat(UUID.randomUUID().toString()));
            customer.setFirstName("Jane ".concat(UUID.randomUUID().toString()));
            customer.setLastName("Doe ".concat(UUID.randomUUID().toString()));
            Customer createdCustomer = customer.create();
            System.out.println("Created customer: " + createdCustomer);
            Customer.builder().build().delete(createdCustomer.getId());

            System.out.println("Retrieved customer: " + customer.retrieve(createdCustomer.getId()));
        } catch (DeepStackException e) {
            System.out.println(
                    "Customer delete ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getDeepStackError());
        }
    }
}

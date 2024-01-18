package com.deepstack.example.customer;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.service.Customer;

import java.io.IOException;
import java.util.UUID;

public class CustomerCreate {
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
            customer.setClientCustomerId("John Doe ".concat(UUID.randomUUID().toString()));
            customer.setFirstName("John");
            customer.setLastName("Doe");
            Customer createdCustomer = customer.create();
            System.out.println("Create customer: " + createdCustomer);

            System.out.println("Retrieved customer: " + customer.retrieve(createdCustomer.getId()));
        } catch (DeepStackException e) {
            System.out.println(
                    "Customer create ---> Code: "
                            + e.getCode()
                            + "\nMsg: "
                            + e.getMessage()
                            + "\nApi error: "
                            + e.getDeepStackError());
        }
    }
}

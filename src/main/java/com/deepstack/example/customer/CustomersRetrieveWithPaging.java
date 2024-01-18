package com.deepstack.example.customer;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.service.Customer;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersRetrieveWithPaging {
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
      Map<String, String> queryParams = new HashMap<>();
      queryParams.put("size", "1");
      queryParams.put("index", "1");

      List<Customer> customers = customer.list(queryParams, null);
      System.out.println("Retrieved customers: " + customers);
      customers.forEach(
          c -> System.out.println("Customer: " + c.getFirstName() + " " + c.getLastName()));
    } catch (DeepStackException e) {
      System.out.println(
          "Customer retrieve ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
    }
  }
}

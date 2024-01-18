# GloballyPaid Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/com.globallypaid/globallypaid-java.svg?label=Maven%20Central)](https://search.maven.org/artifact/com.globallypaid/globallypaid-java)
[![Build Status](https://github.com/globallypaid/globallypaid-sdk-java/workflows/CI/badge.svg)](https://github.com/globallypaid/globallypaid-sdk-java/actions)
[![JavaDoc](http://img.shields.io/badge/javadoc-reference-blue.svg)][javadoc]

The official [Deepstack] Java library.

# Table of Contents

* [Installation](#installation)
* [Documentation](#documentation)
* [Samples](#samples)
* [Quick Start](#quick-start)
    * [Make a Charge Sale Transaction](#charge-trans)
    * [Make a Charge Sale Transaction with Javascript SDK integration](#charge-trans-js-sdk)
* [Configuring Timeouts](#timeouts)   
* [About](#about)

<a name="installation"></a>
## Installation

## Installation Notice

Until further notice, the current installation methods will not work since the deployments are not synced. This will be changed soon, but for now, all SDK installations should be done through the Deepstack Github.

### Requirements

- Java 1.8 or later

#### Gradle users

Add this dependency to your project's build file in the root:

```groovy
...
dependencies {
    ...
    implementation "com.deepstack:deepstack-java:1.0.2"
}

repositories {
    mavenCentral()
}
...
```

#### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.deepstack</groupId>
  <artifactId>deepstack-java</artifactId>
  <version>1.0.2</version>
</dependency>
```

<a name="documentation"></a>
## Documentation

Please see the [Java API docs][api-docs] for the most
up-to-date documentation.

You can also refer to the [online Javadoc][javadoc].

<a name="samples"></a>
## Samples

For a sample project, please visit [GloballyPaid Java SDK samples][java-sdk-sample].

For a comprehensive list of examples, please visit project's [examples][examples].

<a name="quick-start"></a>
## Quick Start

The following is the minimum needed code to make a charge sale transaction:

<a name="env-variables"></a>
### Environment Variables

`deepstack-java` supports the Deepstack Api Key, App ID and Shared Secret 
values stored in the following environment variables:

* `PUBLISHABLE_API_KEY`
* `APP_ID`
* `SHARED_SECRET`
* `USE_SANDBOX`

### Setup Environment Variables

Update the development environment with your [Environment Variables](#env-variables) 
with the following steps:

#### Linux users:

1. Copy the sample environment file `env_sample.sh` to a new file
```bash
cp env_sample.sh deepstack_env.sh
```
2. Edit the new `deepstack_env.sh` to add your [Environment Variables](#env-variables)
3. Source the `deepstack_env.sh` file to set the variables in the current session
```bash
source deepstack_env.sh
```

#### Windows users:

1. Copy the sample environment file `env_sample.bat` to a new file
```bash
cp env_sample.bat deepstack_env.bat
```
2. Edit the new `deepstack_env.bat` to add your [Environment Variables](#env-variables)
3. Execute the `deepstack_env.bat` file to set the variables
```bash
deepstack_env.bat
```

### Initialize the Client
```java
DeepStack deepStack = new DeepStack(
          Config.builder()
              .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
              .appId(System.getenv("APP_ID"))
              .sharedSecret(System.getenv("SHARED_SECRET"))
              .sandbox(System.getenv("USE_SANDBOX")) // true if you need to test through GloballyPaid sandbox
              .build());
```
or
```java
String publishableApiKey = "pk_live_xxxxx";
String appId = "Your APP ID";
String sharedSecret = "Your Shared Secret";

DeepStack deepStack = new DeepStack(
          Config.builder()
              .publishableApiKey(publishableApiKey)
              .appId(appId)
              .sharedSecret(sharedSecret)              
              .build());
```

You can also change to sandbox with the following setting:
```java
DeepStack.setSandbox(true);
```

##### Per-request configuration

All SDK service methods accept an optional `RequestOptions` object, additionally allowing per-request configuration:
```java
RequestOptions requestOptions = RequestOptions.builder()
            .appId("Your APP ID")
            .sharedSecret("Your Shared Secret")
            .build();

Customer.builder().build().retrieve("customer_id_here", requestOptions);

RequestOptions requestOptions = RequestOptions.builder()
            .publishableApiKey("Your Publishable API Key").build();

DeepStack.builder().build().token("token_request_here", requestOptions);
```

<a name="timeouts"></a>
### Configuring Timeouts

Connect and read timeouts can be configured globally:

```java
DeepStack.setConnectTimeout(50 * 1000); // in milliseconds
DeepStack.setReadTimeout(100 * 1000);
```

Or on a finer grain level using `RequestOptions`:

```java
RequestOptions options = RequestOptions.builder()
    .connectTimeout(50 * 1000) // in milliseconds
    .readTimeout(100*1000)
    .build();
DeepStack.builder().build().charge(ChargeRequest.builder().build(), requestOptions);
```

Please take care to set conservative read timeouts. Some API requests can take
some time, and a short timeout increases the likelihood of a problem within our
servers.

<a name="charge-trans"></a>
### Make a Charge Sale Transaction

GloballyPaid Charge Sale Transaction example:

```java
package com.deepstack.example.payment;

import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.model.ChargeRequest;
import com.deepstack.model.ChargeResponse;
import com.deepstack.service.DeepStack;
import java.io.IOException;

public class ChargeSaleTransaction {
  public static void main(String[] args) throws IOException, DeepStackException {
    try {
      DeepStack deepStack =
          new DeepStack(
              Config.builder()
                  .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                  .appId(System.getenv("APP_ID"))
                  .sharedSecret(System.getenv("SHARED_SECRET"))
                  .sandbox(System.getenv("USE_SANDBOX"))
                  .build());

      ChargeRequest chargeRequest =
          ChargeRequest.builder()
              .source("source") // can be the token or payment instrument identifier
              .amount(130)
              .currencyCode("USD")
              .clientCustomerId("XXXXXXX") // set your customer id
              .clientInvoiceId("XXXXXX") // set your invoice id
              .clientTransactionId("XXXXXXXXX")
              .clientTransactionDescription("Charge Sale Transaction") // set your transaction description
              .capture(true) // sale charge
              .savePaymentInstrument(false)
              .build();

      ChargeResponse chargeResponse = deepStack.charge(chargeRequest, null);
      System.out.println(chargeResponse);
    } catch (DeepStackException e) {
      System.out.println(
          "ChargeSaleTransaction ---> Code: "
              + e.getCode()
              + "\nMsg: "
              + e.getMessage()
              + "\nApi error: "
              + e.getDeepStackError());
      throw e;
    }
  }
}
```

See the project's [examples][examples] for more examples.

<a name="charge-trans-js-sdk"></a>
### Make a Charge Sale Transaction with Javascript SDK integration

```java
import com.deepstack.exception.DeepStackException;
import com.deepstack.http.Config;
import com.deepstack.http.RequestOptions;
import com.deepstack.model.ChargeRequest;
import com.deepstack.model.ChargeResponse;
import com.deepstack.model.PaymentInstrumentToken;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

  public ChargeResponse charge(PaymentInstrumentToken paymentInstrumentToken)
      throws DeepStackException {

    ChargeResponse chargeResponse = null;

    DeepStack deepStack =
        new DeepStack(
            Config.builder()
                .publishableApiKey(System.getenv("PUBLISHABLE_API_KEY"))
                .appId(System.getenv("APP_ID"))
                .sharedSecret(System.getenv("SHARED_SECRET"))
                .sandbox(System.getenv("USE_SANDBOX"))
                .build());

    if (paymentInstrumentToken != null && !paymentInstrumentToken.getId().isEmpty()) {
      RequestOptions requestOptions =
          RequestOptions.builder().connectTimeout(50 * 1000).readTimeout(100 * 1000).build();

      ChargeRequest chargeRequest =
          ChargeRequest.builder()
              .source(paymentInstrumentToken.getId())
              .amount(160)
              .currencyCode("USD")
              .clientCustomerId("XXXXXXX")
              .clientInvoiceId("XXXXXX")
              .clientTransactionId("XXXXXXXXX")
              .clientTransactionDescription("Charge Sale sample!")
              .capture(true)
              .savePaymentInstrument(false)
              .build();

      chargeResponse = deepStack.charge(chargeRequest, requestOptions);
      System.out.println(chargeResponse.toString());
    }
    return chargeResponse;
  }
}
```

Please visit [Deepstack Java SDK samples][java-sdk-sample] to see sample project (Pending updates please refer to the samples above for now).

<a name="about"></a>
## About
deepstack-java is maintained and funded by Deepstack.

If you've found a bug in the library or would like new features added, 
go ahead and [open issues][java-sdk-open-issues] or [pull requests][java-sdk-pull-requests]!

[globallypaid]: https://www.deepstack.io/
[api-docs]: https://qa-v2.docs.globallypaid.com/
[javadoc]: https://www.javadoc.io/doc/com.globallypaid/globallypaid-java/latest/index.html
[java-sdk-open-issues]: https://github.com/globallypaid/globallypaid-sdk-java/issues
[java-sdk-pull-requests]: https://github.com/globallypaid/globallypaid-sdk-java/pulls
[examples]: https://github.com/globallypaid/globallypaid-sdk-java/blob/master/src/main/java/com/globallypaid/example/
[java-sdk-sample]: https://github.com/globallypaid/globallypaid-sdk-java-samples
[support-center]: https://www.deepstack.io/#sandbox

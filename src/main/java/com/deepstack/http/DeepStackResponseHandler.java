package com.deepstack.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

public class DeepStackResponseHandler extends AbstractResponseHandler<String> {
  @Override
  public String handleEntity(HttpEntity entity) throws IOException {
    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
  }

  @SneakyThrows
  @Override
  public String handleResponse(final HttpResponse response) {
    final HttpEntity entity = response.getEntity();
    return entity == null ? null : handleEntity(entity);
  }
}

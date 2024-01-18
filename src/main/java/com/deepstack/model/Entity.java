package com.deepstack.model;

import com.deepstack.util.JsonUtils;

import java.io.IOException;

/** Class to convert object to/from JSON. */
public class Entity {
  /**
   * Create a string representation of the object JSON.
   *
   * @return a JSON string.
   * @throws IOException in case of a JSON marshal error.
   */
  public String toJson() throws IOException {
    return JsonUtils.convertFromObjectToJson(this);
  }

  /**
   * Create an Object representation of the JSON string.
   *
   * @param json a JSON string.
   * @param var Object from converted JSON string.
   * @return an object.
   * @throws IOException in case of a JSON marshal error.
   * @param <T> Class type to convert to.
   */
  public <T> T fromJson(String json, Class<T> var) throws IOException {
    return JsonUtils.convertFromJsonToObject(json, var);
  }
}

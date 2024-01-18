package com.deepstack.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

/** Utility class for to/from json converting */
public class JsonUtils {
  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * Convert Object into Json.
   *
   * @param obj Content to convert
   * @return JSON string from Object
   * @throws JsonProcessingException In case there is a problem during JSON content processing.
   */
  public static String convertFromObjectToJson(Object obj) throws JsonProcessingException {
    return mapper.writeValueAsString(obj);
  }

  /**
   * Convert JSON into Object.
   *
   * @param json JSON to convert
   * @param var Object from converted JSON string
   * @param <T> Class type to convert to
   * @return Object
   * @throws IOException In case there is a problem during JSON content processing.
   */
  public static <T> T convertFromJsonToObject(String json, Class<T> var) throws IOException {
    return mapper.readValue(json, var);
  }

  /**
   * Convert JSON into List.
   *
   * @param json JSON to convert
   * @param var Object from converted JSON string
   * @param <T> Class type to convert to
   * @return List of objects
   * @throws IOException In case there is a problem during JSON content processing.
   */
  public static <T> List<T> convertFromJsonToList(String json, Class<T> var) throws IOException {
    JavaType javaType = getCollectionType(List.class, var);
    return mapper.readValue(json, javaType);
  }

  /**
   * Get generic Collection Type @Param collectionClass generic Collection @Param elementClasses
   * element class @Return JavaType Java type
   *
   * @param collectionClass Collection class type
   * @param elementClasses Class type to convert to
   * @since 1.0
   * @return Type of Class
   */
  public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }
}

package hr.leapwise.expression.evaluator.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class TestUtil {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> asJsonMap(final String json) {
        try {
            return new ObjectMapper().readValue(json, new TypeReference<>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

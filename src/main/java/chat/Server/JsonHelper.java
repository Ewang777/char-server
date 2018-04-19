package chat.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * created by ewang on 2018/4/19.
 */
public class JsonHelper {
    static ObjectMapper mapper = null;

    static {
        mapper = new ObjectMapper();
        //禁用未知属性打断反序列化功能
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public static <T> String encode(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("write json fail: " + object, e);
        }
    }

    public static <T> T decode(String data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("parse json fail: requiredType=" + clazz + ", json=" + data, e);
        }

    }

}

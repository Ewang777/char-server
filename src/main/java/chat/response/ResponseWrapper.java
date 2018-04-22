package chat.response;


import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/4/20.
 */
public class ResponseWrapper {

    private final boolean success;

    private final String errMessage;

    // TODO figure it out
    // Map<String,Object> xxx, xxx.put(String key,T value) works, json => Object
    // Object xxx, constructor(T ooo){ xxx=ooo; } doesn't work, json => array
    private final Map<String, Object> data = new HashMap<>();

    public ResponseWrapper() {
        this.success = true;
        this.errMessage = null;
    }

    public ResponseWrapper(String errMessage) {
        this.success = false;
        this.errMessage = errMessage;
    }

    public <T> ResponseWrapper addObject(T value, String key) {
        if (key != null) {
            data.put(key, value);
        }
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }
}

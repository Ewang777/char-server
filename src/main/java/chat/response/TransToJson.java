package chat.response;

import chat.Helper.JsonHelper;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * created by ewang on 2018/4/18.
 */
public class TransToJson implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }
        outputJson((HttpServletResponse) webRequest.getNativeResponse(), JsonHelper.encode(returnValue));
        mavContainer.setRequestHandled(true);
    }

    public static void outputJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json; charset=utf-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
    }

}

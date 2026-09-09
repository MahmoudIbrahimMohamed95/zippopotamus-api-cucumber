package responseUtils;
import io.restassured.response.Response;

public class ResponseUtils {

    public static String safeBody(Response response) {
        if (response == null) {
            return "";
        }
        try {
            return response.getBody().asPrettyString();
        } catch (Exception e) {
            return response.getBody().asString();
        }
    }

    public static String unexpectedStatusMessage(Response response) {
        return "Unexpected HTTP status. Response body was: " + safeBody(response);
    }
}
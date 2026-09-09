package configFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import readers.Log;
import readers.PropertyReader;

public class RequestSpecificationBuilder {
    public static final String BASE_URI =  PropertyReader.getProperty("baseUrl");

    public static RequestSpecification getRequestSpecification() {
        RequestSpecification reqSpec= new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setAccept(ContentType.JSON)
                .build();
        Log.info("RequestSpecification is created");
        return reqSpec;
    }
}
package context;
import io.restassured.response.Response;
import pojo.deserialization.CityLocationResponse;

public class ScenarioContext {
    private String requestedCountry;
    private String requestedPostalCode;
    private CityLocationResponse cityLocationResponse;
    private Response response;

    public String getRequestedCountry() {
        return requestedCountry;
    }

    public void setRequestedCountry(String requestedCountry) {
        this.requestedCountry = requestedCountry;
    }

    public String getRequestedPostalCode() {
        return requestedPostalCode;
    }

    public void setRequestedPostalCode(String requestedPostalCode) {
        this.requestedPostalCode = requestedPostalCode;
    }

    public CityLocationResponse getResponse() {
        return cityLocationResponse;
    }

    public void setResponse(CityLocationResponse cityLocationResponse) {
        this.cityLocationResponse = cityLocationResponse;
    }

    public void setRawResponse(Response response) {
        this.response = response;
    }

    public Response getRawResponse() {
        return response;
    }
}
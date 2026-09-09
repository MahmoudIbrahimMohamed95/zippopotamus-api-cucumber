package apiClients;
import configFactory.RequestSpecificationBuilder;
import io.restassured.response.Response;
import readers.Log;

import static io.restassured.RestAssured.given;

public class CountryApi {
        private static final String ENDPOINT_PATH = "/{country}/{postalCode}";

        public  Response getLocationByCountryAndPostalCode(String country, String postalCode) {
            Response response=  given()
                    .spec(RequestSpecificationBuilder.getRequestSpecification())
                    .pathParam("country", country)
                    .pathParam("postalCode", postalCode)
                    .when()
                    .get(ENDPOINT_PATH);
            Log.info("Request for Location of postal code " + postalCode + "in country" + country + "is sent");
            return response;
        }
    }
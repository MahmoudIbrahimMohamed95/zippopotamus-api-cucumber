package stepDefinitions;
import apiClients.CountryApi;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.deserialization.CityLocationResponse;
import pojo.deserialization.Place;
import responseUtils.ResponseUtils;
import java.util.List;

public class CityLocationSteps {
    private final ScenarioContext scenarioContext;
    private final CountryApi countryApi;
    private static final String ATTACHMENT_TYPE_TEXT = "text/plain";
    private static final String ATTACHMENT_TYPE_JSON = "application/json";
    private static final String SCHEMA_PATH = "schemas/city-location-response-schema.json";

    public CityLocationSteps(ScenarioContext scenarioContext, CountryApi countryApi) {
        this.scenarioContext = scenarioContext;
        this.countryApi = countryApi;
    }

    @Given("a postal code lookup request for country {string} and postal code {string}")
    public void aPostalCodeLookupRequestForCountryAndPostalCode(String country, String postalCode) {
        scenarioContext.setRequestedCountry(country);
        scenarioContext.setRequestedPostalCode(postalCode);

        Allure.addAttachment("Request parameters", ATTACHMENT_TYPE_TEXT,
                "country = " + country + System.lineSeparator() + "postalCode = " + postalCode);
    }

    @When("the request is sent to the city location service")
    public void theRequestIsSentToTheCityLocationService() {
        Response response = countryApi.getLocationByCountryAndPostalCode(scenarioContext.getRequestedCountry(),
                scenarioContext.getRequestedPostalCode());
        scenarioContext.setRawResponse(response);
        scenarioContext.setResponse(response.as(CityLocationResponse.class));

        Allure.addAttachment("Response status", ATTACHMENT_TYPE_TEXT,
                String.valueOf(response.getStatusCode()));
        Allure.addAttachment("Response body", ATTACHMENT_TYPE_JSON, ResponseUtils.safeBody(response));
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        Response response = scenarioContext.getRawResponse();
        int actualStatus = response.getStatusCode();

        Allure.addAttachment("Status code", ATTACHMENT_TYPE_TEXT, String.valueOf(expectedStatus),
                String.valueOf(actualStatus));

        Assert.assertEquals(actualStatus, expectedStatus, ResponseUtils.unexpectedStatusMessage(response));
    }

    @Then("the response should contain the country {string}")
    public void theResponseShouldContainTheCountry(String expectedCountry) {
        String actualCountry = scenarioContext.getResponse().getCountry();

        Allure.addAttachment("Country", ATTACHMENT_TYPE_TEXT, expectedCountry, actualCountry);

        Assert.assertEquals(actualCountry, expectedCountry);
    }

    @Then("the response should contain at least one place")
    public void theResponseShouldContainAtLeastOnePlace() {
        List<Place> places = scenarioContext.getResponse().getPlaces();
        int actualCount = places == null ? 0 : places.size();

        Allure.addAttachment("Place count", ATTACHMENT_TYPE_TEXT, "at least 1",
                String.valueOf(actualCount));

        Assert.assertNotNull(places);
        Assert.assertFalse(places.isEmpty());
    }

    @Then("the returned postal code should exactly match the requested postal code")
    public void theReturnedPostalCodeShouldExactlyMatchTheRequestedPostalCode() {
        String requested = scenarioContext.getRequestedPostalCode();
        String returned = scenarioContext.getResponse().getPostCode();

        Allure.addAttachment("Postal code", ATTACHMENT_TYPE_TEXT, requested, returned);

        Assert.assertEquals(returned, requested);
    }

    @Then("the response should conform to the city location schema")
    public void theResponseShouldConformToTheCityLocationSchema() {
        scenarioContext.getRawResponse().then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(SCHEMA_PATH));
    }
}
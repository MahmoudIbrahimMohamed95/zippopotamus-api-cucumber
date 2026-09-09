package pojo.deserialization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CityLocationResponse {

    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    @JsonProperty("post code")
    private String postCode;

    private List<Place> places;

    public String getCountry() {
        return country;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public String getPostCode() {
        return postCode;
    }

    public List<Place> getPlaces() {
        return places;
    }
}
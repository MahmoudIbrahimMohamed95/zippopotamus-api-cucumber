@api @cityLocation
Feature: City location lookup by country and postal code
  As a consumer of the postal code lookup service
  I want to retrieve the location details for a given country and postal code
  So that I can display or validate address information

  @positive @smoke
  Scenario Outline: Retrieve location details for a valid country and postal code
    Given a postal code lookup request for country "<country>" and postal code "<postalCode>"
    When the request is sent to the city location service
    Then the response status should be 200
    And the response should contain the country "<expectedCountry>"
    And the response should contain at least one place
    And the returned postal code should exactly match the requested postal code
    And the response should conform to the city location schema

    Examples: Numeric, alphanumeric, spaced and hyphenated postal code formats
      | country | postalCode | expectedCountry |
      | DE      | 01067      | Germany          |
      | US      | 90210      | United States    |
      | CA      | A0A        | Canada           |
      | CZ      | 100 00     | Czech Republic   |
      | BR      | 01000-000  | Brazil           |

  @positive
  Scenario: Country code lookup is case-insensitive
    Given a postal code lookup request for country "de" and postal code "01067"
    When the request is sent to the city location service
    Then the response status should be 200
    And the response should contain the country "Germany"

  @negative
  Scenario Outline: Requesting a location the service cannot resolve returns a not found response
    Given a postal code lookup request for country "<country>" and postal code "<postalCode>"
    When the request is sent to the city location service
    Then the response status should be 404

    Examples: Non-existent postal code, invalid country, malformed input
      | country | postalCode           |
      | DE      | 99999                |
      | XX      | 12345                |
      | DE      | ABCDE                |
      | DE      | ';DROP TABLE places--|
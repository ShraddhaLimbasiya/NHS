Feature: NHS Job Search functionality

  As a jobseeker on NHS Jobs website
  I want to search for a job with my preferences
  So that I can get recently posted job results

  Scenario Outline: Search jobs with preferred job title, location, distance and sort by newest date posted
    Given I am on the NHS Jobs search page
    When I enter preferences such as "<JobTitle>" in the Job Title and "<City>" in the Location
    And I enter the distance as "<distance>" in the distance
    And I click the search button
    Then I should see a list of job results that match my preferences
    And I should be able to sort results by "Date Posted (newest)"
    Then the jobs should be sorted by newest Date Posted
    Examples:
      | JobTitle     | City   | distance|
      | Data Analyst | London | 10       |

  Scenario: Search by title only
    Given I am on the NHS Jobs search page
    When I enter "Nurse" in the Job Title field
    And I leave the Location field empty
    And I click the search button
    Then I should see a list of job results that include "Nurse" in the title

  Scenario: Search with only location
    Given I am on the NHS Jobs search page
    When I enter "Manchester" in the Location field
    And I click the Search button
    Then I should see job results that match my preferences

  Scenario: Search with no input
    Given I am on the NHS Jobs search page
    When I click the Search button
    Then I should see a list of all recent jobs sorted by newest Date Posted

  Scenario: Search with invalid job title
    Given I am on the NHS Jobs search page
    When I enter "@@##$%" in the Job title field
    And I click the Search button
    Then I should see a message indicating no results were found

  Scenario: Search with very long job title input
    Given I am on the NHS Jobs search page
    When I enter "Nurse" repeated 50 times in the Job title field
    And I click the Search button
    Then I should see a message indicating no results were found

  Scenario: Search for a non-existent job
    Given I am on the NHS Jobs search page
    When I enter "Underwater Basket Weaver" in the Job title field
    And I click the Search button
    Then I should see a message indicating no results were found

  Scenario: Navigate to page 2 of results
    Given I perform a job search with many results
    When I click to go to the second page
    Then I should see a new set of job results

  Scenario: Use postcode in location field
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the Search button
    Then I should see job results near the postcode
  Scenario: Clicking on a job opens the job detail page
    Given I perform a search for "Administrator"
    When I click on a job in the result list
    Then I should be taken to the job detail page

  Scenario: Browser back button returns to search results
    Given I perform a search for "Healthcare Assistant"
    And I click on a job in the result list
    When I press the browser back button
    Then I should return to the same search results
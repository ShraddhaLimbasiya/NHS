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
      | JobTitle     | City   | distance |
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
    And I click the search button
    Then I should see a list of job results that match my preferences

  Scenario: Search with no input
    Given I am on the NHS Jobs search page
    When I click the search button
    And I should be able to sort results by "Date Posted (newest)"
    Then the jobs should be sorted by newest Date Posted
    And I click on a job in the result list
    Then I should be taken to the job detail page
    And I press the browser back button
    Then I should return to the same search results

  Scenario: Search with invalid job title as special characters
    Given I am on the NHS Jobs search page
    When I enter special characters "@@##$%" in the Job title field
    And I click the search button
    Then I should see a message indicating no results were found

  Scenario: Search with very long job title input
    Given I am on the NHS Jobs search page
    When I enter "Nurse" repeated 50 times in the Job title field
    And I click the search button
    Then I should see a message indicating no results were found


  Scenario: Search for a non-existent job
    Given I am on the NHS Jobs search page
    When I enter non existent title as "cherry ####tomato" in the Job title field
    And I click the search button
    Then I should see a message indicating no results were found

  Scenario Outline: Navigate to page 2 of results
    Given I am on the NHS Jobs search page
    When I enter preferences such as "<JobTitle>" in the Job Title and "<City>" in the Location
    And I enter the distance as "<distance>" in the distance
    And I click the search button
    When I click to go to the second page
    Examples:
      | JobTitle     | City   | distance |
      | Data Analyst | London | 10       |

  Scenario: Use postcode in location field
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    Then I should see a list of job results that match my preferences




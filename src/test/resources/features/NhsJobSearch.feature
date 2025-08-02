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

  @smoke
  Scenario: Filter job results by working pattern
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "Working pattern":
      | Full time |
      | Part time |
    And I click the Apply filters button
    Then I should see a list of job results that match my "Working pattern"

  Scenario: Filter job results by contract type
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "Contract type":
      | Voluntary |
      | Bank      |
    And I click the Apply filters button
    Then I should see a list of job results that match my preferences

  Scenario: Filter job results by salary range
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "Pay range":
      | 40000 |
    And I click the Apply filters button
    Then I should see a list of job results that match my preferences

  Scenario: Filter job results by staff group
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "Staff group":
      | Nursing              |
      | Midwifery Registered |
    And I click the Apply filters button
    Then I should see a list of job results that match my preferences

  Scenario: Filter job results by job reference number
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "NHS pay grades and schemes":
      | Band 4 |
      | Band 5 |
    And I click the Apply filters button
    Then I should see a list of job results that match my preferences


  Scenario: Apply multiple filters and view refined job results
    Given I am on the NHS Jobs search page
    When I enter "SW1A 1AA" in the Location field
    And I click the search button
    And I select "Working pattern":
      | Full time |
      | Part time |
    And I select "Contract type":
      | Voluntary |
      | Bank      |
    And I select "Pay range":
      | 40000 |
    And I select "Staff group":
      | Nursing              |
      | Midwifery Registered |
    And I select "NHS pay grades and schemes":
      | Band 4 |
      | Band 5 |
    And I click the Apply filters button
    When I click the Clear filters button
    Then all filters should be reset
    And I should see the unfiltered job results for the original search






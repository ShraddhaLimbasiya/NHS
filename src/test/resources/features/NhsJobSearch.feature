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

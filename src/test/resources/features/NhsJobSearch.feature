Feature: NHS Job Search functionality

  Scenario Outline: Search jobs with preferences and sort by newest date posted
    Given I am on the NHS Jobs search page
    When I enter preferences such as "<JobTitle>" in the Job Title and "<City>" in the Location
    And I click the search button
    Then I should see a list of job results that match my preferences
    And I should be able to sort results by "Date Posted (newest)"
    Then the jobs should be sorted by newest Date Posted
    Examples:
      | JobTitle | City   |
      | Data Analyst    | London |
